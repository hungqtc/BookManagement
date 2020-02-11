package com.hung.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
