package de.cit.backend.api.converter;

import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class UserConverter implements Converter<UserDTO, User>{

	public UserDTO convertToBackend(User user) {
		UserDTO userDto = new UserDTO();
		
		userDto.setId(user.getID());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setRegisteredSince(user.getRegisteredSince());
		return userDto;
	}

	public User convertToFrontend(UserDTO userDto) {
		User user = new User();
		
		user.setID(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setRegisteredSince(userDto.getRegisteredSince());
		return user;
	}


}
