package browser;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.qpid.jms.JmsQueueBrowser;

public class PriotityQueueBrowser {

	public static void main(String[] args) throws NamingException, JMSException {
		Context context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Queue queue = (Queue) context.lookup("myPriorityQueueLookup");
		Connection connection = factory.createConnection("guest", "guest");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		QueueBrowser browser = session.createBrowser(queue);
		JmsQueueBrowser qBrowser = (JmsQueueBrowser) browser.getEnumeration();
		while (qBrowser.hasMoreElements()) {
			TextMessage msg = (TextMessage) qBrowser.nextElement();
			System.out.println(msg.getText() + " Message Priority: " + msg.getJMSPriority());
		}
		connection.close();
	}

}
