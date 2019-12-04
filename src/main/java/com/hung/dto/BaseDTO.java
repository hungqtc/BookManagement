package com.hung.dto;

import java.util.Date;

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
public class BaseDTO {
	private Long id;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
}
