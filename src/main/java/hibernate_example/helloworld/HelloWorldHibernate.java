package hibernate_example.helloworld;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HelloWorldHibernate {

	public static void main(String[] args) {

		SessionFactory sessionFactory = new MetadataSources(
				new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml").build()
		).buildMetadata().buildSessionFactory();

		Message m = new Message();
		m.setText("Test");
		m.setRecipient("anevis Solutions");

		Session s =  sessionFactory.getCurrentSession();

		final Transaction transaction = s.beginTransaction();

		s.save(m);

		transaction.commit();

		s = sessionFactory.getCurrentSession();

		final Transaction t2 = s.beginTransaction();

		final Criteria criteria = s.createCriteria(Message.class);
		Message m1 = (Message) criteria.uniqueResult();

		System.out.println(m1.getRecipient());

		t2.commit();

		s.close();

		sessionFactory.close();

	}

}
