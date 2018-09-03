package hibernate_example.helloworld;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HelloWorldJPA {



	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("HelloWorldPU");



	}


}
