package hibernate_example.helloworld;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

class HelloWorldHibernate {

	public static void main(String[] args) {

		SessionFactory sessionFactory = new MetadataSources(
				new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml").build()
		).buildMetadata().buildSessionFactory();

		Message m = new Message();
		m.setText("Test");
		m.setRecipient("anevis Solutions");

		Message n = new Message();
		n.setText("Hallo");
		n.setRecipient("Weihnachtsmann");

		Session s =  sessionFactory.getCurrentSession();

		final Transaction transaction = s.beginTransaction();

		s.save(m);
		s.save(n);

		transaction.commit();

		s = sessionFactory.getCurrentSession();

		s.close();

		sessionFactory.close();

	}

}
