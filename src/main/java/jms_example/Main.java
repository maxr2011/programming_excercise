package jms_example;

import jms_example.helloworld.ExampleMessageReceiver;

import javax.jms.JMSException;

class Main {

	public static void main(String[] args) throws JMSException {

		final ExampleMessageReceiver receiver = new ExampleMessageReceiver();
		receiver.startListener();





	}

}
