package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.States;

public interface StatesRepository extends JpaRepository<States, String> {

	
//	select methods
	
	public States findByStateId(String stateId);

	public List<States> findByReceivedFundInRupeesGreaterThanEqual(double receivedFund);

	public List<States> findByNgoCountLessThanEqual(int ngoCount);

	public List<States> findByNgoCountGreaterThanEqual(int ngoCount);
	
	@Query("select s.ngoCount from States s where s.stateId=:stateId")
	public int findNgoCountByStateId(String stateId);

	@Query("select s.receivedFundInRupees from States s where s.stateId=:stateId")
	public String findReceivedFundInRupeesByStateId(String stateId);
	
	
	
	
}
