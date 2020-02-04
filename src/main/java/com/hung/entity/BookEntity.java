
package com.hung.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "book")
public class BookEntity extends BaseEntity {

	@Column(name = "title")
	private String title;
	
	@Column(name = "non_utf8_title")
	private String noUtf8Title;

	@Column(name = "author")
	private String author;
	
	@Column(name = "non_utf8_author")
	private String noUtf8Author;

	@Column(name = "description")
	private String description;

	@Column(name = "image")
	private String image;
	
	@Column(name = "status")
	private int status;

	@OneToMany(mappedBy = "book")
	private List<CommentEntity> comments = new ArrayList<CommentEntity>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
}
