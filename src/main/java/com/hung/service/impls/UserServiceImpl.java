package com.hung.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hung.config.security.CustomUserDetails;
import com.hung.converter.UserConverter;
import com.hung.dto.UserDTO;
import com.hung.entity.RoleEntity;
import com.hung.entity.UserEntity;
import com.hung.exceptions.UserExistionException;
import com.hung.repository.RoleRepository;
import com.hung.repository.UserRepository;
import com.hung.service.MailService;
import com.hung.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	MailService mailService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserConverter userConverter;
	
	@Override
	public List<UserDTO> findAll() {
		List<UserEntity> listEntity = userRepository.findAll();
		return userConverter.toDTO(listEntity);
	}

	@Override
	public UserDTO findById(Long id) {
		UserEntity entity = userRepository.findById(id).get();
		return UserConverter.toDTO(entity);
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		
		UserEntity userEntity = new UserEntity();
		if (userDTO.getId() != null) {
			UserEntity oldUserEntity = userRepository.findById(userDTO.getId()).get();
			if (!userDTO.getEmail().equals(oldUserEntity.getEmail()) && (userRepository.findByEmail(userDTO.getEmail()) != null)) {
				throw new UserExistionException();
			}
			userEntity = userConverter.toEntity(userDTO, oldUserEntity);
		} else {
			if (userRepository.findByEmail(userDTO.getEmail()) != null) {
				throw new UserExistionException();
			}
			userEntity = userConverter.toEntity(userDTO);
		}

		ArrayList<String> listRole = (ArrayList<String>) userDTO.getRoles();
		ArrayList<RoleEntity> listRoleEntity = new ArrayList<RoleEntity>();
		for (int i = 0; i < listRole.size(); i++) {

			RoleEntity roleEntity = roleRepository.findOneByName(listRole.get(i));
			listRoleEntity.add(roleEntity);
		}
		userEntity.setRoles(listRoleEntity);
		userEntity = userRepository.save(userEntity);
		if (userRepository.save(userEntity) != null) {
			mailService.sendMail(userDTO);
		}
		return UserConverter.toDTO(userEntity);
	}

	@Override
	public void delete(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}

	@Override
	public UserDetails loadUserById(Long id) {
		UserEntity user = userRepository.findById(id).get();
		return new CustomUserDetails(user);
	}
}
