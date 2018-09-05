package exercise.jms;

import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	private JmsOperations jmsOperations;

	public Sender(JmsTemplate jmsTemplate) {
		this.jmsOperations = jmsTemplate;
	}

	public void sendMessage(String msg) {
		jmsOperations.send(session -> session.createTextMessage(msg));
	}

}
