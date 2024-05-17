package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

	@Id
	@Column(length = 13)
	@Pattern(regexp = "^USER-+[A-Z]{2}+-[0-9]{5}$", message = "Please provide vali user id in the format [USER-XX-00001]")
	private String userId;
	
	@Column(nullable = false)
	@Pattern(regexp = "^[a-zA-Z0-9\s]{3,}$", message = "User name should be atleast of 3 characters in length, with no special characters")
	private String userName;
	
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Please provide valid email address")
	private String userEmail;
	
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^(0|91)?[6-9][0-9]{9}$", message = "Please provide valid phone number. 1. It should be 10 digits long.\r\n"+ "2. The first digit should be a number. 6 to 9.\r\n"+ "3. The rest 9 digits should be any number. 0 to 9.\r\n"+ "4. It can have 11 digits including 0 at the starting.\r\n"+ "5. It can have 12 digits including 91 at the starting.")
	private String userPhone;
	
	@Column(nullable = false, unique = true, length = 20)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "The new password  must contain length length at least 8 characters and maximum of 20 characters and it must contain contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;
	
	@Pattern(regexp = "^[A-Z]{2}$", message = "The state id must be in format [XX]")
	private String stateId;
}
