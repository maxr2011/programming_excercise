package exercise.jms;

import exercise.jms.Receiver.Receiver;
import jms_config.JMSConfigReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("unused")
class Main {

	// Mainmethode für den Receiver
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JMSConfigReceiver.class);
		Receiver rc = (Receiver) ap.getBean("receiver");
	}

}
