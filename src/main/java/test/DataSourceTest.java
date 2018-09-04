package test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring_config.DataSourceConfiguration;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= DataSourceConfiguration.class)
@ActiveProfiles("JDBC")
public class DataSourceTest {

	@Autowired
	private DataSource ds;


	@Test
	public void dsShouldNotBeNull() { assertNotNull(ds); }

}
