package jms_example;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Properties;

/**
 man muss die gf-client.jar im originalverzeichnis lassen !
 das liegt an den relativen verweisen, die in dieser jar sind !
 */

public class Sender
{
	public static void main(String[] args)
	{
		System.out.println("-------------------------------------------");
		System.out.println("jms sender");
		System.out.println("-------------------------------------------");
		System.out.println("Begin " + new Date(System.currentTimeMillis()) + "\n");

		try
		{
			// step 1 create initialcontext
			System.out.println("about to create initialcontext");
			Properties env = new Properties();
			env.put("org.omg.CORBA.ORBInitialHost","127.0.0.1"); // default ist localhost !!
			env.put("org.omg.CORBA.ORBInitialPort","3700"); // ist default
			env.put("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
			InitialContext ctx = new InitialContext(env); // NamingException
			// für glassfish reicht default-constructor
			//InitialContext ctx = new InitialContext(); // NamingException
			System.out.println("initialcontext received\n");

			// step 2 lookup connection factory
			System.out.println("try to lookup QueueConnectionFactory");
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("jms/MyQueueConnectionFactory");
			System.out.println("QueueConnectionFactory received: " + queueConnectionFactory.getClass());
			Class[] interfaces = queueConnectionFactory.getClass().getInterfaces();
			System.out.println("omplemented interfaces:");
			for(Class face : interfaces)
				System.out.println(face);
			System.out.println();

			if (queueConnectionFactory != null)
			{
				// step 3 use connection factory to create a JMS connection
				System.out.println("try to create a QueueConnection");
				QueueConnection queueConnection = queueConnectionFactory.createQueueConnection(); // JMSException
				System.out.println("JMS QueueConnection created\n");

				// step 4 use connection to create a session
				System.out.println("try to create a QueueSession");
				QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); // JMSException
				System.out.println("QueueSession created" + queueSession);
				System.out.println();

				// step 5 lookup the Queue
				System.out.println("try to lookup the Queue");
				Queue queue = (Queue) ctx.lookup("jms.MyQueue");
				System.out.println("Queue received: " + queue);
				System.out.println();

				queueConnection.start(); // JMSException
				System.out.println("queueConnection started");

				System.out.println("create sender\n");
				QueueSender queueSender = queueSession.createSender(queue);
				System.out.println("QueueSender created" + queueSender);
				String text2send = "mary had a little lamb";
				TextMessage tm = queueSession.createTextMessage(text2send);
				queueSender.send(tm);
				System.out.println("Text:");
				System.out.println(tm.getText());
				System.out.println("gesendet um");
				System.out.println(new Date(System.currentTimeMillis()));
				System.out.println();

				queueSession.close();
				queueConnection.stop();
				queueConnection.close();

				System.out.println("Session closed");
				System.out.println("Connection closed");
				System.out.println("Sender shutdown");
			}
		}
		catch(NamingException | JMSException ex)
		{
			ex.printStackTrace();
		}
		System.out.println("\nEnd " + new Date(System.currentTimeMillis()));
	}
}
