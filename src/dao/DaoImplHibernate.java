package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao{

	private Session session;
	private Transaction transaction;

	@Override
	public void connect() {
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
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
			this.connect();
			transaction = session.beginTransaction();

			// We create a manual query. Remember that "*" does not exist
			Query<Product> query = session.createQuery("select u from Product u");

			// We get a List of products
			List<Product> products = query.list();

			inventory.addAll(products);

			for (Product u : products) {
				System.out.println(u);
			}

			transaction.commit();
			System.out.println("GetInventory Successfully.");

		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
		finally {
			this.disconnect();
		}
		return inventory;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		try {
			this.connect();
			transaction = session.beginTransaction();
			for(Product product : products) {
				ProductHistory productHistory = new ProductHistory(product);
				session.save(productHistory);
			}
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		finally {
			this.disconnect();
		}
		return false;
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		try {
			this.connect();
			Query<Employee> query = session.createQuery("select e from Employee e where e.id = :id and e.password = :password", Employee.class);
			query.setParameter("id", employeeId);
			query.setParameter("password", password);
			employee = query.getSingleResult();
		}
		finally {
			this.disconnect();
		}
		return employee;
	}

	@Override
	public void addProduct(Product product) {
		try {
			this.connect();
			transaction = session.beginTransaction();
			session.save(product);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		finally {
			this.disconnect();
		}
	}

	@Override
	public void updateProduct(Product product) {
		try {
			this.connect();
			transaction = session.beginTransaction();
			
			Query<Product> query = session.createQuery("select u from Product u where u.name = :name",Product.class);
				
			query.setParameter("name", product.getName());

			Product originalProduct = query.uniqueResult();
		    
			if (originalProduct != null) {
		    	originalProduct.setStock(product.getStock());
		    }
			
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		finally {
			this.disconnect();
		}
	}

	@Override
	public void deleteProduct(int productId) {
		try {
			this.connect();
			transaction = session.beginTransaction();
			Product product = session.get(Product.class, productId);
			session.delete(product);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		finally {
			this.disconnect();
		}
		
	}

}
