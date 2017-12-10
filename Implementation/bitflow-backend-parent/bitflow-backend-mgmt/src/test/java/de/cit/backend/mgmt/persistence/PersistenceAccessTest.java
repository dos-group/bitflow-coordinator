package de.cit.backend.mgmt.persistence;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class PersistenceAccessTest {

	private static EntityManager em;
	
	@BeforeClass
    public static void createInput() throws IOException {
		em = PersistenceManager.instance();
	}
	
	@Test
	public void findUserTest(){
		UserDTO user = em.find(UserDTO.class, 1);
		System.out.println(user.getCreatedProjects().size());
		System.out.println(user.getJoinedProjects().size());
		System.out.println(user.getEmail());
	}
	
	@Test
	public void findProjectTest(){
		ProjectDTO pro = em.find(ProjectDTO.class, 1);
		
		System.out.println(pro.getName());
		System.out.println(pro.getUserdata().getName());
		System.out.println(pro.getProjectMembers().size());
	}
}
