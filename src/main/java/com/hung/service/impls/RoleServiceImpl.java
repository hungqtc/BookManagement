package com.hung.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hung.converter.RoleConverter;
import com.hung.dto.RoleDTO;
import com.hung.entity.RoleEntity;
import com.hung.entity.UserEntity;
import com.hung.repository.RoleRepository;
import com.hung.repository.UserRepository;
import com.hung.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	 @Autowired
	 private RoleRepository roleRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RoleConverter roleConverter;
	 
	 @Override
		public List<RoleDTO> getAll() {
			List<RoleEntity> listEntity = roleRepository.findAll();
			return roleConverter.toDTO(listEntity);
		}

		@Override
		public RoleDTO getById(Long id) {
			RoleEntity entity = roleRepository.findOne(id);
			return roleConverter.toDTO(entity);
		}

		@Override
		public RoleDTO save(RoleDTO roleDTO) {
			RoleEntity roleEntity = new RoleEntity();
			
			if (roleDTO.getId() != null) {
				RoleEntity oldRoleEntity = roleRepository.findOne(roleDTO.getId());
				roleEntity = roleConverter.toEntity(roleDTO, oldRoleEntity);
			} else {
				roleEntity = roleConverter.toEntity(roleDTO);
			}
			ArrayList<String> listUserDTO = (ArrayList<String>) roleDTO.getUsers();
			ArrayList<UserEntity> listUserEntity = new ArrayList<UserEntity>();
			for (int i = 0; i < listUserDTO.size(); i++) {
				
				UserEntity userEntity = userRepository.findOneByName(listUserDTO.get(i));
				listUserEntity.add(userEntity);
			}
			
		/* roleEntity.setUsers(listUserEntity); */
			
			roleEntity = roleRepository.save(roleEntity);
			return roleConverter.toDTO(roleEntity);
		}

		@Override
		public void delete(Long RoleId) {
			roleRepository.delete(RoleId);
		}
}
