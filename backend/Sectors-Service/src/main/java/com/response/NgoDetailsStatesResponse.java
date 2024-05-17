package com.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NgoDetailsStatesResponse {

	private String ngoId;
	
	private String ngoName;
	
	private String city;
	
	private String address;
	
	private double receivedFundInRupees;
	
	private StatesResponse statesResponse;
}
