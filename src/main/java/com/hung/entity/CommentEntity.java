package com.hung.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

	private String message;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookEntity book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

}
