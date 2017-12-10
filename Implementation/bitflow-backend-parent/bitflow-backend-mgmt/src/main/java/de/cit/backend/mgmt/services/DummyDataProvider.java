package de.cit.backend.mgmt.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.cit.backend.mgmt.persistence.model.Pipeline;
import de.cit.backend.mgmt.persistence.model.Project;
import de.cit.backend.mgmt.persistence.model.Userdata;

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
		Set<Pipeline> set = new HashSet<>();
		set.add(getDummyPipeline(1,1));
		project.setPipelines(set);
		
		return project;
	}
	
	public static Pipeline getDummyPipeline(int id, int step){
		Pipeline pipe = new Pipeline();
		pipe.setId(id);
		pipe.setScript("Bitflow-script");
		pipe.setStatus("running");
		
		return pipe;
	}
	
}
