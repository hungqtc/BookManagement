package com.hung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hung.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	@Query("select c from CommentEntity c where c.book = ?1")
	CommentEntity findByBook(int bookId);

	@Query("select c from CommentEntity c where c.createdBy = ?1")
	CommentEntity findByUser(String name);
	
	/*
	 * @Query(value = "delete from Comment c where c.user_id = ?1", nativeQuery =
	 * true) void deleteByUserCreated(long id);
	 */
	@Transactional
	@Modifying
	@Query("delete from CommentEntity c where c.user.id = ?1")
	void deleteByUserCreated(long id);
	
	@Transactional
	@Modifying
	@Query("delete from CommentEntity c where c.book.id = ?1")
	void deleteByBook(long id);

}
