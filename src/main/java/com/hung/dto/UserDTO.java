package com.hung.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends BaseDTO{
	@NotBlank(message = "Please provide mail")
	@Email(message = "Please provide correct email")
	private String email;
	@NotBlank(message = "Please provide password")
	private String password;
	private int status;
	private List<String> roles = new ArrayList<String>();
}
