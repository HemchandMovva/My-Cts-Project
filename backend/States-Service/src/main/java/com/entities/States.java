package com.entities;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class States {

	@Id
	@Pattern(regexp = "^[A-Z]{2}$", message = "The state id must match the pattern [XX]. It must contain two uppercase characteres only")
	private String stateId;
	
	@Column(unique = true)
	@NotBlank(message = "The state name must not be blank")		// when we enter the spaces validates		
	@Pattern(regexp = "^[a-zA-Z0-9\s]{3,}$", message = "State name must has length atleast 6 with no special characters")
	private String stateName;
	
	@Min(value = 0, message = "The ngo count must be greater than or equal to zero")
	private int ngoCount;
	
	@Min(value = 0, message = "The receivedFundInRupees must be greater than or equal to zero")
	private double receivedFundInRupees;


}
