package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exceptions.InvalidIdException;
import com.response.StatesResponse;

import jakarta.validation.Valid;

@FeignClient(name = "STATES-SERVICE", url = "http://localhost:100", path = "StatesControllerImpl")
public interface SectorsStatesFeign {

	@GetMapping("/selectById/{stateId}")
	public StatesResponse selectById(@Valid @PathVariable String stateId) throws InvalidIdException;
}
