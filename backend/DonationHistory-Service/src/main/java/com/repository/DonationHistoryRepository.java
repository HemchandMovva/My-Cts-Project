package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.DonationHistory;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, String> {

	@Query("select count(dh)from DonationHistory dh")
	public int getCount();
	
	public DonationHistory findByDonationId(String donationId);

	@Query("select d from DonationHistory d where d.userId=:userId")
	public List<DonationHistory> findByUserId(String userId);
}
