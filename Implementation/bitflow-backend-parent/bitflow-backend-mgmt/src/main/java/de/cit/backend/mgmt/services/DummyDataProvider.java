package de.cit.backend.mgmt.services;

import java.util.Date;

import de.cit.backend.persistence.model.Pipeline;
import de.cit.backend.persistence.model.Project;
import de.cit.backend.persistence.model.Userdata;

public class DummyDataProvider {

	public static Userdata getDummyUser(){
		Userdata user = new Userdata();
		user.setId(1);
		user.setEmail("cit@test.de");
		user.setName("TestUser");
		user.setRegisteredSince(new Date());
		
		return user;
	}
	
	public static Project getDummyProject(){
		Project project = new Project();
		project.setId(1);
		project.setCreatedAt(new Date());
		project.setName("TestProject");
		project.setUserdata(getDummyUser());
		
		return project;
	}
	
	public static Pipeline getDummyPipeline(){
		Pipeline pipe = new Pipeline();
		pipe.setId(1);
		pipe.setStepNumber(1);
		pipe.setProject(getDummyProject());
		pipe.setStatus("running");
		
		return pipe;
	}
	
}
