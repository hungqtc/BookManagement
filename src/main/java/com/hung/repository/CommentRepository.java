package com.hung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hung.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	CommentEntity findByCreatedBy(String name);
	
	@Transactional
	@Modifying
	@Query("delete from CommentEntity c where c.user.id = ?1")
	void deleteByUserCreated(long id);
	
	@Transactional
	@Modifying
	@Query("delete from CommentEntity c where c.book.id = ?1")
	void deleteByBook(long id);
	
	@Query("select c from CommentEntity c where c.book.id = ?1")
	List<CommentEntity> findByBook(long id);
}
