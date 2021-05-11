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

public class PrioritySender {

	public static void main(String[] args) throws NamingException {

		Context context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Destination queue = (Destination) context.lookup("myPriorityQueueLookup");
		Connection connection = null;
		try {
			connection = factory.createConnection("guest", "guest");
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			for (int i = 0; i < 10; i++) {
				MessageProducer messageProducer = session.createProducer(queue);
				messageProducer.setPriority(i);
				TextMessage msg = session.createTextMessage("Test Message " + i);
				messageProducer.send(msg);
				messageProducer.close();
			}

			System.out.println("Message Sending successful");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
