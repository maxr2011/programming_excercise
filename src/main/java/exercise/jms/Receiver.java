package exercise.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Receiver {

	private JmsOperations jmsOperations;

	public Receiver(JmsTemplate jmsTemplate) {
		this.jmsOperations = jmsTemplate;
	}

	@JmsListener(destination = "queue")
	public String receiveMessage() {
		try {
			TextMessage tm = (TextMessage) jmsOperations.receive();
			String text = tm.getText();

			System.out.println(text);
			if(text.equals("Data written to country_table")) {
				System.out.println("some code");
			}

			return text;
		} catch (JMSException jme) {
			jme.printStackTrace();
		}
		return null;
	}

//	@JmsListener(destination = "queue")
//	public boolean isSent() {
//		if(receiveMessage().equals("Data written to country_table")) return true;
//		else return false;
//	}

}
