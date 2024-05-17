package com.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {

	private String userId;
	
	private String userName;
	
	private String userEmail;
	
	private String userPhone;
	
	private String password;
	
	private String stateId;
}
