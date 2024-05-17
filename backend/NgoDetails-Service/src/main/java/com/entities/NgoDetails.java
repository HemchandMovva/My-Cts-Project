package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NgoDetails {

	@Id
	@Column(length = 12)
	@Pattern(regexp = "^NGO-+[A-Z]{2}-+[0-9]{5}$", message = "Ngo id must in format [NGO-XX-00001]")
	
	private String ngoId;
	@Column(length = 510, nullable = false)
	private String ngoName;
	
	private String city;
	
	@Column(length = 1020)
	private String address;
	
	@Min(value = 0, message = "The received fund details must be greater than or equal to 0")
	private double receivedFundInRupees;
	
	@Pattern(regexp = "^[A-Z]{2}$", message = "State id must in format [XX] with uppercase charcaters")
	private String stateId;
	
}
