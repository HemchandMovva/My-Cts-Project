package com.response;

import lombok.Data;

@Data
public class StatesResponse {

	private String stateId;
	private String stateName;
	private int ngoCount;
	private double receivedFundInRupees;
}
