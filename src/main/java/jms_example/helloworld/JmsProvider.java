package jms_example.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

class JmsProvider {

	public static ConnectionFactory getConnectionFactory () {
         /*The VM transport allows clients to connect to each other inside
                 the VM without the overhead of the network communication. */

		return new ActiveMQConnectionFactory("tcp://localhost:61616");
	}
}
