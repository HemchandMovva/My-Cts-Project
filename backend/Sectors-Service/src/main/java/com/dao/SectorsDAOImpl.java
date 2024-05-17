package com.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.entities.Sectors;
import com.exceptions.ExceptionMessages;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.feign.SectorsNgoDetailsFeign;
import com.feign.SectorsStatesFeign;
import com.repository.SectorsRepository;
import com.response.NgoDetailsResponse;
import com.response.NgoDetailsSectorsResponse;
import com.response.NgoDetailsStatesResponse;
import com.response.SectorsNgoDetailsResponse;
import com.response.SectorsResponse;
import com.response.StatesResponse;

@Component
@ComponentScan("com.configuration")
public class SectorsDAOImpl {
	
	@Autowired
	public SectorsNgoDetailsFeign secotrsNgoDetailsFeign;
	
	@Autowired 
	public SectorsStatesFeign sectorsStatesFeign;
	
	@Autowired
	public SectorsRepository sectorsRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Autowired
	public ExceptionMessages exceptionMessages;
	
	
	public String generateNgoId(String stateId, int cnt) {
		return "NGO"+"-"+stateId+"-"+String.format("%05d", cnt);
	}
	
//	----------------------------------MAPPING TO RESPONSE CLASSES -------------------------------------------------
	
	public NgoDetailsSectorsResponse mapToNgoDetailsSectorsResponse(NgoDetailsStatesResponse ngoDetails, List<Sectors> sectorsList) {
		//mapping
		NgoDetailsResponse ngoDetailsResponse = modelMapper.map(ngoDetails, NgoDetailsResponse.class);
		//SET THE STATE ID
		ngoDetailsResponse.setStateId(ngoDetails.getStatesResponse().getStateId());
		//mapping
		List<SectorsResponse> sectorsResponseList = sectorsList.stream().map(x -> modelMapper.map(x,  SectorsResponse.class)).collect(Collectors.toList());
		NgoDetailsSectorsResponse ngoDetailsSectorsResponse = new NgoDetailsSectorsResponse(ngoDetailsResponse, sectorsResponseList);
		return ngoDetailsSectorsResponse;
	}
	
	public SectorsNgoDetailsResponse mapToSectorsNgoDetailsResponse(List<Sectors> sectorsList, String sectorName) throws InvalidIdException {
		
		//GETTING THE CONSTRUCTOR
		SectorsNgoDetailsResponse sectorsNgoDetailsResponse = new SectorsNgoDetailsResponse();
		//SET SECTOR NAME
		sectorsNgoDetailsResponse.setSectorName(sectorName);
		
		List<NgoDetailsResponse> ngoDetailsResponseList = new ArrayList<>();
		
		for(Sectors s: sectorsList) {
			String ngoId = s.getNgoId();
			NgoDetailsStatesResponse ngoDetailsStatesResponse = secotrsNgoDetailsFeign.selectById(ngoId);
			NgoDetailsResponse ngoDetailsResponse = modelMapper.map(ngoDetailsStatesResponse, NgoDetailsResponse.class);
			ngoDetailsResponse.setStateId(ngoDetailsStatesResponse.getStatesResponse().getStateId());
			ngoDetailsResponseList.add(ngoDetailsResponse);
		}
		sectorsNgoDetailsResponse.setNgoDetailsResponseList(ngoDetailsResponseList);
		
		return sectorsNgoDetailsResponse;
	}
	
	
//	--------------------1. SELECT QUERY METHODS----------------------------------------------------------------------------------------

	public NgoDetailsSectorsResponse selectByNgoId(String ngoId) throws InvalidIdException{
		
		NgoDetailsStatesResponse ngoDetails =secotrsNgoDetailsFeign.selectById(ngoId);
		if(ngoDetails != null) {
			List<Sectors> sectorsList = sectorsRepository.findByNgoId(ngoId);
			return mapToNgoDetailsSectorsResponse(ngoDetails, sectorsList);
		}else {
			throw new InvalidIdException("There is no ngo with ngo id: "+ngoId);
		}
	}
	
	public SectorsNgoDetailsResponse selectNgosBySectorName(String sectorName) throws InvalidIdException{
		
		List<Sectors> sectorsList = sectorsRepository.findBySectorName(sectorName);
		
		return mapToSectorsNgoDetailsResponse(sectorsList, sectorName);
	}
	
//	--------------------2. INSERT QUERY METHODS-----------------------------------------------------------------------------------------
	public String insertDetails(Sectors obj, String ngoId) throws InvalidDetailsException, InvalidIdException{
						
		List<Sectors> lis = sectorsRepository.findByNgoId(ngoId);
		//modify true condition with lis.isEmpty()
		if(!lis.isEmpty()) {
			//CHECKING THE AVAILABILITY OF THE EXISTING SECTOR UNDER SAME NGO
			boolean avail = false;
			for(Sectors sector: lis) {
				if(sector.getSectorName().equals(obj.getSectorName())) {
					avail=true;
					break;
				}
			}
			if(!avail) {
				obj.setNgoId(ngoId);
				sectorsRepository.save(obj);
				return "Successfully added sector "+obj.getSectorName()+" under ngo "+ngoId;
			}else {
				throw new InvalidDetailsException(exceptionMessages.getInvalidDetailsExceptionMessage(obj));
			}	
		}else {
			throw new InvalidIdException("There is no ngo with ngo id: "+ngoId);
		}
	}
	
	public void insertRawSectors(String filePath, String stateId) throws InvalidIdException, IOException{
		
		StatesResponse states = sectorsStatesFeign.selectById(stateId);
		if(states != null) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String data;
			int count = 1;
			while((data = bufferedReader.readLine()) != null) {
				String[] rawNgoData = data.split(":");
				String ngoId = generateNgoId(stateId, count);
				String[] sectorsList = rawNgoData[5].split(",");
				
				for(String sect: sectorsList) {
					Sectors sector = new Sectors();
					sector.setSectorName(sect);
					try {
						insertDetails(sector, ngoId);
					}
					catch(Exception e) {
						System.err.println(e);
						break;
					}
				}
				count+=1;
			}
		}else {
			throw new InvalidIdException("There is no state with state id :"+stateId);
		}
	}
	
//	---------------------3. UPDATE QUERY METHODS----------------------------------------------------------------------------------------
	public String updateName(String oldName, String newName, String ngoId) throws InvalidIdException, InvalidNameException {
		NgoDetailsStatesResponse ngoDetailsResponse = secotrsNgoDetailsFeign.selectById(ngoId);
		if(ngoDetailsResponse != null) {
			List<Sectors> sectors = sectorsRepository.findByNgoId(ngoId);
			for(Sectors sector : sectors) {
				if(sector.getSectorName().equals(oldName)) {
					if(!newName.isBlank() & newName.length()>3) {
						sector.setSectorName(newName);
						sectorsRepository.save(sector);
						return "Successfully updated sector name from "+oldName+" to "+newName+" in "+ngoId;
					}else {
						throw new InvalidNameException(exceptionMessages.getInvalidNameExceptionMessage(newName));
					}
				}
			}
			throw new InvalidNameException("There is no sector with name "+oldName);
			
		}else {
			throw new InvalidIdException("There is no ngo with ngo id: "+ngoId);
		}
	}
	
//	---------------------4. DELETE QUERY METHODS-----------------------------------------------------------------------------------------
	public String deleteById(String ngoId, String sectorName) throws InvalidIdException {
		NgoDetailsStatesResponse ngoDetailsResponse = secotrsNgoDetailsFeign.selectById(ngoId);
		if(ngoDetailsResponse != null) {
			sectorsRepository.deleteByNgoIdSectorName(ngoId, sectorName);
			return "Successfully deleted the sector "+sectorName+" from ngo "+ngoId;
		}else {
			throw new InvalidIdException("There is no ngo with ngo id: "+ngoId);
		}
	}
}
