package com.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.entities.DonationHistory;
import com.exceptions.InvalidIdException;
import com.feign.NgoDetailsFeign;
import com.feign.SectorsFeign;
import com.feign.StatesFeign;
import com.feign.UserDetailsFeign;
import com.repository.DonationHistoryRepository;
import com.response.DonationHistoryResponse;
import com.response.NgoDetailsResponse;
import com.response.NgoDetailsSectorsResponse;
import com.response.SectorsResponse;
import com.response.UserDetailsResponse;

@Component
@ComponentScan("com.configuration")
public class DonationHistoryDAOImpl {

	
	@Autowired
	private DonationHistoryRepository donationHistoryRepository;
	
	@Autowired
	private UserDetailsFeign userDetailsFeign;
	
	@Autowired
	private NgoDetailsFeign ngoDetailsFeign;
	
	@Autowired
	private SectorsFeign sectorsFeign;
	
	@Autowired
	private StatesFeign statesFeign;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public String generateDonationId() {
		int count = donationHistoryRepository.getCount()+1;
		return "DON"+String.format("%05d", count);
	}
	
	public String generateDateAndTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dateTime = localDateTime.format(dateTimeormatter);
		return dateTime;
	}
	
//	-----------------------------1SELECT METHODS --------------------------------------------------
	public DonationHistoryResponse selectById(String donationId) throws InvalidIdException{
		
		DonationHistory donationDetails = donationHistoryRepository.findByDonationId(donationId);
		if(donationDetails != null) {
			return modelMapper.map(donationDetails, DonationHistoryResponse.class);
		}else {
			throw new InvalidIdException("There is no donation history with id: "+donationId);
		}
	}
	
	public List<DonationHistory> selectByUserId(String userId) {
		return donationHistoryRepository.findByUserId(userId);
	}
//	-----------------------------2.INSERT METHODS --------------------------------------------------
	public String insertDetails(DonationHistory donationHistory, String userId) throws InvalidIdException{
		
		UserDetailsResponse userDetailsResponse = modelMapper.map(userDetailsFeign.selectById(userId), UserDetailsResponse.class);
		
//		Object obj = userDetailsFeign.selectById(userId);
		
		
		
		if(userDetailsResponse != null) {
			
			String donationId = generateDonationId();
			
			String date = generateDateAndTime();
			
			donationHistory.setDonationId(donationId);
			
			donationHistory.setDate(date);
			
			NgoDetailsResponse ngoDetailsResponse  = modelMapper.map(ngoDetailsFeign.selectById(donationHistory.getNgoId()), NgoDetailsResponse.class);
			
			if(ngoDetailsResponse != null) {
			
				NgoDetailsSectorsResponse ngoDetailsSectorsResponse = sectorsFeign.selectByNgoId(ngoDetailsResponse.getNgoId());
				
				List<SectorsResponse> sectorsResponseList = ngoDetailsSectorsResponse.getSectorsResponseList();
				
				boolean validSector = false;
				
				for(SectorsResponse sector: sectorsResponseList) {
					
					if(sector.getSectorName().equals(donationHistory.getSector())) {
						validSector = true;
						break;
					}
				}
				
				if(validSector) {
					
					donationHistory.setUserId(userId);
					
					statesFeign.updateReceivedFundInRupeesByStateId(userDetailsResponse.getStateId(), donationHistory.getDonatedFundInRupees());

					donationHistoryRepository.save(donationHistory);
					
					
					
					return "Successfully added donation history details with id: "+donationId;
					
				}
				else {
					throw new InvalidIdException("There is no sector with name: "+donationHistory.getSector());
				}
			}
			else {
				throw new InvalidIdException("There is no ngo with id: "+ donationHistory.getNgoId());
			}
		}
		else {
			throw new InvalidIdException("There is no user with id: "+userId);
		}
	}
}
