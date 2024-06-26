package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sectors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sNo;
	
	@Column(nullable = false)
	private String sectorName;
	
	@Column(nullable = false)
	private String ngoId;
	
}
