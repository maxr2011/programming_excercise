package jms_example;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Properties;

public class Receiver
{
	public static void main(String[] args)
	{
		System.out.println("-------------------------------------------");
		System.out.println("receiver");
		System.out.println("-------------------------------------------");
		System.out.println("Begin " + new Date(System.currentTimeMillis()) + "\n");

		try
		{
			// initialize JNDI
			// step 1 create initialcontext
			System.out.println("about to create initialcontext");
			Properties env = new Properties();
			env.put("org.omg.CORBA.ORBInitialHost", "localhost"); // default ist localhost !!
			env.put("org.omg.CORBA.ORBInitialPort", "3700"); // ist default
			env.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
			//InitialContext ctx = new InitialContext(env); // NamingException
			InitialContext ctx = new InitialContext(); // NamingException
			System.out.println("initialcontext received\n");

			// step 2 lookup connection factory
			System.out.println("try to lookup QueueConnectionFactory");
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("jms/MyQueueConnectionFactory");
			System.out.println("QueueConnectionFactory received\n");
			// wenn nix da ist -> wartet endlos

			// step 3 use connection factory to create a JMS connection
			System.out.println("try to create a QueueConnection");
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection(); // JMSException
			System.out.println("JMS QueueConnection created\n");

			// step 4 use connection to create a session
			System.out.println("try to create a QueueSession");
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("QueueSession created\n");

			// step 5 lookup the Queue
			System.out.println("try to lookup the Queue");
			Queue queue = (Queue) ctx.lookup("jms.MyQueue");
			System.out.println("Queue received\n");

			queueConnection.start();

			System.out.println("create QueueReceiver");
			QueueReceiver queueReceiver = queueSession.createReceiver(queue);
			System.out.println("QueueReceiver created");
			System.out.println("set MessageListener for receiver\n");
			queueReceiver.setMessageListener(new MyMessageListener());
			System.out.println("waiting for messages\n");
			// blockiert bis eine message kommt

			queueSession.close();
			queueConnection.stop();
			queueConnection.close();
			System.out.println("Session closed");
			System.out.println("Connection closed");
			System.out.println("Receiver shutdown");

		}
		catch(NamingException | JMSException ex)
		{
			ex.printStackTrace();
		}
	}


	private static class MyMessageListener implements MessageListener
	{
		// listener to receive messages
		public void onMessage(Message msg)
		{
			TextMessage tm = (TextMessage) msg;
			try
			{
				System.out.println(
						"onMessage (" + new Date(System.currentTimeMillis()) + "), received text :\n" + tm.getText() + "\n");
			}
			catch(JMSException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
