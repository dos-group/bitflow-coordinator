package de.cit.backend.mgmt.persistence;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.Userdata;

public class PersistenceAccessTest {

	private static EntityManager em;
	
	@BeforeClass
    public static void createInput() throws IOException {
		em = PersistenceManager.instance();
	}
	
	@Test
	public void findUserTest(){
		Userdata user = em.find(Userdata.class, 1);
		System.out.println(user.getEmail());
	}
}
