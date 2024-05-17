package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.entities.Sectors;

import jakarta.transaction.Transactional;

public interface SectorsRepository extends JpaRepository<Sectors, Integer> {

	@Query("select s from Sectors s where s.ngoId=:ngoId")
	public List<Sectors> findByNgoId(String ngoId);

	@Query("select s from Sectors s where s.sectorName=:sectorName")
	public List<Sectors> findBySectorName(String sectorName);

	@Modifying
	@Transactional
	@Query("delete from Sectors s where s.ngoId=:ngoId and s.sectorName=:sectorName")
	public void deleteByNgoIdSectorName(String ngoId, String sectorName);
	

}
