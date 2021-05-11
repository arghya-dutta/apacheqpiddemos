package receiver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleReceiver {

	public static void main(String[] args) throws NamingException, JMSException {
		Context context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Destination queue = (Destination) context.lookup("myQueueLookup");
		Connection connection = factory.createConnection("guest", "guest");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageConsumer messageConsumer = session.createConsumer(queue);
		System.out.println(((TextMessage)messageConsumer.receive(3000)).getText());
		connection.close();

	}

}
