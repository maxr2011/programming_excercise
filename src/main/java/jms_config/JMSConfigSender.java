package jms_config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
@EnableJms
@ComponentScan(basePackages = {"exercise.jms.Sender"})
public class JMSConfigSender {

	@Bean
	Destination destination() {
		return new ActiveMQQueue("queue");
	}

	@Bean
	ConnectionFactory connectionFactory() {

		return new ActiveMQConnectionFactory("tcp://localhost:61616");

	}

	@Bean
	JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {

		final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(destination());
		return jmsTemplate;

	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1-1");
		return factory;
	}

}
