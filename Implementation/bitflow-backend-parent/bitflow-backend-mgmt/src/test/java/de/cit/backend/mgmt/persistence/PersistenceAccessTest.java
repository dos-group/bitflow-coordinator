package de.cit.backend.mgmt.persistence;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.PersistenceManager;

public class PersistenceAccessTest {

	private static PersistenceManager pm;
	
	@BeforeClass
    public static void createInput() throws IOException {
		pm = PersistenceManager.instance();
	}
	
	@Test
	public void testConnection(){
		System.out.println("No real test, just for testing the DB connection. If no connection can be established,"
				+ " the init of this test fails!");
	}
}
