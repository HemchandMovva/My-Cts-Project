package com.response;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StatewiseUserDetailsResponse {

	private StatesResponse statesResponse;
	private List<UserDetailsResponse> statewiseUserDetails;
}
