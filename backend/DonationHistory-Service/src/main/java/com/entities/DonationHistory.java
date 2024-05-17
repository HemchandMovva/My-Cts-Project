package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationHistory {

	@Id
	private String donationId;
	
	private String userId;
	
	@Column(nullable = false)
	private String sector;
	
	@Column(nullable = false, length=12)
	private String ngoId;
	
	@Column(nullable = false)
	private String date;
	
	@Column(nullable = false)
	private double donatedFundInRupees;
}
