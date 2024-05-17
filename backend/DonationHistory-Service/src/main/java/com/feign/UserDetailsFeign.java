package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exceptions.InvalidIdException;

@FeignClient(name = "USERDETAILS-SERVICE", url = "http://localhost:300", path = "/UserDetailsControllerImpl")
@Component
public interface UserDetailsFeign {

	@GetMapping(value = "/selectById/{userId}")
	public Object selectById(@PathVariable String userId) throws InvalidIdException;
}
