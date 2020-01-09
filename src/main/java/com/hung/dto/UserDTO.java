package com.hung.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO extends BaseDTO{
	@NotBlank(message = "Please provide username")
	private String username;
	@NotBlank(message = "Please provide password")
	private String password;
	private int status;
	private List<String> roles = new ArrayList();
}
