package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exceptions.InvalidIdException;
import com.response.NgoDetailsSectorsResponse;

@FeignClient(name = "SECTORS-SERVICE", url = "http://localhost:500", path = "SectorsControllerImpl")
@Component
public interface SectorsFeign {

	@GetMapping("/selectByNgoId/{ngoId}")
	public NgoDetailsSectorsResponse selectByNgoId(@PathVariable String ngoId) throws InvalidIdException;
}
