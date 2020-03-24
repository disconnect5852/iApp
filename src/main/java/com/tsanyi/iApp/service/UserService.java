package com.tsanyi.iApp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsanyi.iApp.DTO.UserDTO;
import com.tsanyi.iApp.db.entity.Email;
import com.tsanyi.iApp.db.entity.User;
import com.tsanyi.iApp.db.repository.UserRepository;

@Service
public class UserService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String VALIDATION_MSG = "Element: %s %s. Value= %s";

	@Autowired
	private UserRepository userRepo;

//	@Autowired
//	private EmailRepository emailRepo;

	@Autowired
	private Validator validator;
	
	@Transactional
	public List<UserDTO> getAllUsers() {
		log.debug("Getting all users");
		var users = userRepo.findAll();
		var userList = new ArrayList<UserDTO>();
		users.forEach(user -> userList.add(convertEntityToDTO(user)));
		return userList;
	}

	public Set<String> createUser(UserDTO dto) {
		var messages = new HashSet<String>();
		var violations = validator.validate(dto);
		if (violations.size() != 0) {
			violations.forEach(vi -> messages.add(String.format(VALIDATION_MSG, vi.getPropertyPath(), vi.getMessage(), vi.getInvalidValue())));
			return messages;
		}
		var entity = convertDTOToEntity(dto);
		log.debug(entity.toString());
		var user = userRepo.save(entity);
		messages.add(user != null ? "User added successfully" : "\"Something Bad Happened\"©");
		return messages;
	}

	private static User convertDTOToEntity(UserDTO dto) {
		var emails = new HashSet<Email>();
		var userEntity = new User(dto.getName(), dto.getBirthDate(), emails);
		dto.getEmails().forEach(email -> emails.add(new Email(email, userEntity)));
		return userEntity;
	}

	private static UserDTO convertEntityToDTO(User user) {
		var emails = new HashSet<String>();
		user.getEmail().forEach(email -> emails.add(email.getEmail()));
		UserDTO dto = new UserDTO(user.getName(), user.getBirthDate(), emails);
		return dto;
	}
}
