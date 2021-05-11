package senders;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleSender {

	public static void main(String[] args) throws NamingException, JMSException {
		Context context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Destination queue = (Destination) context.lookup("myQueueLookup");
		Connection connection = factory.createConnection("guest", "guest");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer messageProducer = session.createProducer(queue);
		TextMessage msg = session.createTextMessage("Test Message");
		messageProducer.send(msg);

		System.out.println("Message Sending successful");
		connection.close();

	}

}
