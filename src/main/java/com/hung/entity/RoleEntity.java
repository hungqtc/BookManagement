package com.hung.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/* @Data */
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{

	@Column(name = "name")
	private String name;
	
	@ManyToMany(  fetch = FetchType.EAGER,   mappedBy = "roles" )
	private List<UserEntity> users ;
	
}
