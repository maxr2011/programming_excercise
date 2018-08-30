package spring;

import static org.junit.Assert.*;

import database.JDBC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= ComponentConfig.class)
public class ComponentTest {

	@Autowired
	private JDBC jd;

	@Test
	public void jdShouldNotBeNull(){
		assertNotNull(jd);
	}

}
