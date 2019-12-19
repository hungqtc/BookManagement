package com.hung.dto.payload;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginResponse implements Serializable{
	private String accessToken;
	private String tokenType = "Bearer";

	public LoginResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
