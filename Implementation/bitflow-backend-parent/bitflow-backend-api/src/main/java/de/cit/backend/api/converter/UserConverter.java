package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.api.model.User;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class UserConverter implements Converter<UserDTO, User>{

	public UserDTO convertToBackend(User user) {
		if(user == null){
			return null;
		}
		
		UserDTO userDto = new UserDTO();
		
		userDto.setId(user.getID());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setRegisteredSince(user.getRegisteredSince());
		return userDto;
	}

	public User convertToFrontend(UserDTO userDto) {
		if(userDto == null){
			return null;
		}
		
		User user = new User();
		
		user.setID(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setRegisteredSince(userDto.getRegisteredSince());
		return user;
	}

	public List<User> convertToFrontend(List<UserDTO> userDto) {
			List<User> user = new ArrayList<User>();
			for (UserDTO userDTO : userDto) {
				user.add(this.convertToFrontend(userDTO));
			}
			return user;
	}
	
}
