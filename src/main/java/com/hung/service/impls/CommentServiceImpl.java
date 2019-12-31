package com.hung.service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hung.config.security.CustomUserDetails;
import com.hung.converter.CommentConverter;
import com.hung.dto.CommentDTO;
import com.hung.entity.BookEntity;
import com.hung.entity.CommentEntity;
import com.hung.entity.UserEntity;
import com.hung.repository.BookRepository;
import com.hung.repository.CommentRepository;
import com.hung.repository.UserRepository;
import com.hung.service.CommentService;
import com.hung.utils.SecurityUtil;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentConverter commentConverter;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<CommentDTO> findAll() {
		List<CommentEntity> listEntity = commentRepository.findAll();
		return commentConverter.toDTO(listEntity);
	}

	@Override
	public CommentDTO findById(Long id) {
		CommentEntity entity = commentRepository.findById(id).get();
		return CommentConverter.toDTO(entity);
	}

	@Override
	public CommentDTO save(CommentDTO commentDTO) {
		CommentEntity commentEntity = new CommentEntity();

		if (commentDTO.getId() != null) {
			CommentEntity oldCommentEntity = commentRepository.findById(commentDTO.getId()).get();
			commentEntity = commentConverter.toEntity(commentDTO, oldCommentEntity);
		} else {
			commentEntity = commentConverter.toEntity(commentDTO);
		}
		CustomUserDetails userDetails = SecurityUtil.getPrincipal();
		BookEntity bookEntity = bookRepository.findByTitle(commentDTO.getBookTitle());
		UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());
		commentEntity.setBook(bookEntity);
		commentEntity.setUser(userEntity);
		commentEntity = commentRepository.save(commentEntity);
		return CommentConverter.toDTO(commentEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			commentRepository.deleteById(item);
		}
	}

	@Override
	public int totalItem() {
		return (int) commentRepository.count();
	}

	@Override
	public List<CommentDTO> findAll(Pageable pageable) {
		List<CommentEntity> entities = commentRepository.findAll(pageable).getContent();
		return commentConverter.toDTO(entities);
	}

	@Override
	public void deleteByBook(long[] ids) {
		for (long id : ids) {
			commentRepository.deleteByBook(id);
		}
	}

	@Override
	public void delete(long id) {
		commentRepository.deleteByUserCreated(id);
	}
}
