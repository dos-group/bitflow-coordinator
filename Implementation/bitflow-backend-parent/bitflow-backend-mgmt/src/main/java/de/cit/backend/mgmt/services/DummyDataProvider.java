package de.cit.backend.mgmt.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class DummyDataProvider {

	public static UserDTO getDummyUser(){
		UserDTO user = new UserDTO();
		user.setId(1);
		user.setEmail("cit@test.de");
		user.setName("TestUser");
		user.setRegisteredSince(new Date());
		
		return user;
	}
	
	public static ProjectDTO getDummyProject(){
		ProjectDTO project = new ProjectDTO();
		project.setId(1);
		project.setCreatedAt(new Date());
		project.setName("TestProject");
		project.setUserdata(getDummyUser());
		Set<PipelineDTO> set = new HashSet<>();
		set.add(getDummyPipeline(1,1));
//		project.setPipelines(set);
		
		return project;
	}
	
	public static PipelineDTO getDummyPipeline(int id, int step){
		PipelineDTO pipe = new PipelineDTO();
		pipe.setId(id);
		pipe.setScript("Bitflow-script");
		pipe.setStatus("running");
		
		return pipe;
	}
	
}
