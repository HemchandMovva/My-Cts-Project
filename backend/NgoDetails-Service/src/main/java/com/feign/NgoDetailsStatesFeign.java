package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exceptions.InvalidIdException;
import com.response.StatesResponse;

import jakarta.validation.Valid;

@FeignClient(name = "STATES-SERVICE", url = "http://localhost:100", path = "/StatesControllerImpl")
@Component
public interface NgoDetailsStatesFeign {
	
	@GetMapping("/selectById/{stateId}")
	public StatesResponse selectById(@Valid @PathVariable String stateId) throws InvalidIdException;
	
	@PutMapping(value = "/increaseNgoCountByOne/{stateId}")
	public String increaseNgoCountByOne(@Valid @PathVariable String stateId) throws InvalidIdException;
	
	@PutMapping(value = "/decreaseNgoCountByOne/{stateId}")
	public String decreaseNgoCountByOne(@Valid @PathVariable String stateId) throws InvalidIdException;
	
	@PutMapping(value = "/updateReceivedFundInRupeesByStateId/stateId/{stateId}/fundToBeAdded/{fundToBeAdded}")
	public String updateReceivedFundInRupeesByStateId(@Valid @PathVariable String stateId, @Valid @PathVariable double fundToBeAdded) throws InvalidIdException;
}
