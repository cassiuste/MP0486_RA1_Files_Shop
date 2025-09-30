package dao;

import java.util.ArrayList;

import model.Employee;
import model.Product;

public interface Dao {
	
	public void connect();

	public void disconnect();

	public ArrayList<Product> getInventory();
	
	public boolean writeInventory(ArrayList<Product> products);
	
	public Employee getEmployee(int employeeId, String password);
}
