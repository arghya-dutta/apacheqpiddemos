package receiver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PriotityQueueReceiver {

	public static void main(String[] args) throws NamingException, JMSException {

		Context context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Queue queue = (Queue) context.lookup("myPriorityQueueLookup");
		Connection connection = factory.createConnection("guest", "guest");

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageConsumer messageConsumer = session.createConsumer(queue);
		messageConsumer.setMessageListener(msg -> {
			try {
				TextMessage txtMsg = (TextMessage) msg;
				System.out.println(txtMsg.getText() + " Msg Priority : " + txtMsg.getJMSPriority());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();

	}

}
