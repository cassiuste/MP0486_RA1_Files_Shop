package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Employee;
import model.Product;

public class DaoImplHibernate implements Dao{

	private Session session;
	private org.hibernate.Transaction transaction;

	@Override
	public void connect() {
		Configuration configuration = new Configuration().configure();
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
    	session = sessionFactory.openSession();
	}

	@Override
	public void disconnect() {
		session.close();
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = new ArrayList<>();

		try {
			transaction = session.beginTransaction();

			// We create a manual query. Remember that "*" does not exist
			Query<Product> query = session.createQuery("select u from inventory u");

			// We get a List of Users
			List<Product> products = query.list();

			inventory.addAll(products);

			for (Product u : products) {
				System.out.println(u);
			}

			transaction.commit();
			System.out.println("Get All Successfully.");

		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}

		return inventory;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		
	}

}
