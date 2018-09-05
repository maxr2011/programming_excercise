package exercise.jms;

import jms_config.JMSConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("unused")
public class Main {

	// Mainmethode für den Receiver
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JMSConfig.class);
		Receiver rc = (Receiver) ap.getBean("receiver");
	}

}
