package com.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatesResponse {

	private String stateId;
	private String stateName;
	private int ngoCount;
	private double receivedFundInRupees;
}
