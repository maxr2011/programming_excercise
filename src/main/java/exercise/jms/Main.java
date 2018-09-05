package exercise.jms;

import jms_config.JMSConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	// Mainmethode für den Receiver
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JMSConfig.class);
		Receiver rc = (Receiver) ap.getBean("receiver");

		System.out.println(rc.receiveMessage());
	}

}
