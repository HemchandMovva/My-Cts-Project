package com.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationHistoryResponse {

	private String donationId;
	
	private String userId;
	
	private String sector;
	
	private String ngoId;
	
	private String date;
	
	private double donatedFundInRupees;
}
