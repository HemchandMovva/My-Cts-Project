package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.entities.NgoDetails;

import jakarta.transaction.Transactional;

public interface NgoDetailsRepository extends JpaRepository<NgoDetails, String> {

	public NgoDetails findByNgoId(String id);
	
	@Query("select n from NgoDetails n where n.stateId=:stateId")
	public List<NgoDetails> findByStateId(String stateId);

	@Query("select n.ngoName from NgoDetails n where n.ngoId=:ngoId")
	public String findNgoNameByNgoId(String ngoId);
	
	@Modifying
	@Transactional
	@Query("delete from NgoDetails n where n.ngoId = :ngoId")
	public void deleteByNgoId(String ngoId);

	@Query(" select count(*) from NgoDetails n where n.stateId=:stateId")
	public int findCountByStateId(String stateId);

}
