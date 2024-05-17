package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

	public UserDetails findByUserId(String userId);

	public List<UserDetails> findByStateId(String stateId);

	@Query("select u from UserDetails u where u.userName like %:userName%")
	public List<UserDetails> findByUserName(String userName);

	@Query("select u.userName from UserDetails u where u.userId=:userId")
	public String findUserNameByUserId(String userId);

	@Query("select u.userPhone from UserDetails u where u.userId=:userId")
	public String findUserPhoneByUserId(String userId);

	@Query("select u.userEmail from UserDetails u where u.userId=:userId")
	public String findUserEmailByUserId(String userId);
	
	@Query("select u from UserDetails u where u.userEmail=:email")
	public UserDetails findUserByEmailId(String email);
	
	@Query("select u from UserDetails u where u.userEmail=:userEmail and u.password=:password")
	public UserDetails findUserByEmaildIdAndPassword(String userEmail, String password);
	
	@Query("select u.userId from UserDetails u where u.stateId=:stateId order by u.userId desc limit 1")
	public String selectLastUser(String stateId);
	

}
