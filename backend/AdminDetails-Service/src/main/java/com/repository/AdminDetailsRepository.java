package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.AdminDetails;

import jakarta.transaction.Transactional;

public interface AdminDetailsRepository extends JpaRepository<AdminDetails, String> {

	public AdminDetails findByAdminId(String id);
	
	@Modifying
	@Transactional
	@Query("delete from AdminDetails a where a.adminId=:adminId")
	public void deleteAdminDetailsById(@Param("adminId") String adminId);

}
