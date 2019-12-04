
package com.hung.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hung.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	@Query("select b from BookEntity b where b.createdBy = ?1")
	List<BookEntity> findByCreatedBy(String name, Pageable pageable);
	
	BookEntity findByTitle(String title);
	
	@Query("select b from BookEntity b where b.enabled = ?1")
	List<BookEntity> findByEnabled(int enabled, Pageable pageable);
	
	@Query("select count(1) from BookEntity b where b.createdBy = ?1")
	int count(String userName);
	
	@Query("select count(1) from BookEntity b where b.status = ?1")
	int count(int status);
	
	/*
	 * @Query("select b from BookEntity b where b.createdBy = ?1 LIMIT ")
	 * List<BookEntity> findAllBy(String name, int offset, int limit);
	 */

}
