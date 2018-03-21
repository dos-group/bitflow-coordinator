package de.cit.backend.mgmt.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;


@RunWith(CdiRunner.class)
public abstract class AbstractTestProvider {

	protected static final String ADMIN_NAME = "adminTestUser";
	
    @Inject
    private CdiTestExtension cdiTestExtension;
    private EntityManager entityManager;
    protected EntityTransaction tx;
    
    @Before
    public void initTest() {
        entityManager = cdiTestExtension.getEntityManager();
        tx = entityManager.getTransaction();
        tx.begin();

        beforeTest();

        tx.commit();
        tx.begin();
    }
    
    @After
    public void cleanTest() {
        tx.rollback();
        tx.begin();
        
        afterTest();

        entityManager.flush();
        tx.commit();
		entityManager.clear();
    }
    
    private void afterTest() {
		Query queryUserNative = entityManager.createNativeQuery("delete from citBitDB.USERDATA");
		
		queryUserNative.executeUpdate();
		
    }

    private void beforeTest() {
        UserDTO user = createTestUser(ADMIN_NAME);
    	entityManager.persist(user);
    	
//    	try{
//            String sql = loadSchemaFromFile();
//            Query query = entityManager.createNativeQuery(sql);
//            query.executeUpdate();
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw new IllegalStateException("Schema-Initialisierung fehlgeschlagen!");
//        }
    }
    
    private String loadSchemaFromFile() throws FileNotFoundException, IOException{
        try(InputStream inputStream = ClassLoader.getSystemResourceAsStream("createSchema.sql")){
            String sql = IOUtils.toString(inputStream, Charset.defaultCharset());
            return sql;
        }
    }
    
    protected UserDTO createTestUser(String name){
    	UserDTO user = new UserDTO();
    	user.setEmail("cit@test.de");
    	user.setName(name);
    	user.setPassword("adminPwd");
    	user.setRegisteredSince(new Date());
    	user.setRole(UserRoleEnum.ADMIN);
    	
    	return user;
    }
}
