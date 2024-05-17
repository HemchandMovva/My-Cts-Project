package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.exceptions.InvalidIdException;

import jakarta.validation.Valid;

@FeignClient(name = "STATES-SERVICE", url = "http://localhost:100", path = "StatesControllerImpl")

@Component
public interface StatesFeign {

	@PutMapping(value = "/updateReceivedFundInRupeesByStateId/stateId/{stateId}/fundToBeAdded/{fundToBeAdded}")
	public String updateReceivedFundInRupeesByStateId(@Valid @PathVariable String stateId, @Valid @PathVariable double fundToBeAdded) throws InvalidIdException; 
		
}
