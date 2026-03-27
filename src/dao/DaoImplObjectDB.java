package dao;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Employee;
import model.Product;

public class DaoImplObjectDB implements Dao{

	private EntityManager em;
	
	@Override
	public void connect() {
		EntityManagerFactory emf =
	            Persistence.createEntityManagerFactory(
	                "objects/users.odb");
	        em = emf.createEntityManager();
	}

	@Override
	public void disconnect() {
		 if (em != null && em.isOpen()) {
		        em.close();
		    }
		    
	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		try {
			this.connect();
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeId = :id AND e.password = :pass", 
					Employee.class);
			query.setParameter("id", employeeId);
			query.setParameter("pass", password);
			
			List<Employee> result = query.getResultList();
			if(!result.isEmpty()) {
				employee = result.get(0);
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return employee;
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
