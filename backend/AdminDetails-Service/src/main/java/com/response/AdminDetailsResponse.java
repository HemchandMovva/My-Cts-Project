package com.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetailsResponse {

	private String adminId;
	private String adminName;
	private String adminEmail;
	private String adminPhone;
	private String adminPassword;
	
	private StatesResponse statesResponse;
}
