package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	Connection connection;

	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		String query = "select * from employee where employeeId= ? and password = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setInt(1,employeeId);
    	  	ps.setString(2,password);
    	  	//System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
            	}
            }
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
    	return employee;
	}

	@Override
	public ArrayList<Product> getInventory() {
		this.connect();
		
		String query = "select * from inventory";
		ArrayList<Product> inventory = new ArrayList<Product>();
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		Product product = new Product(rs.getInt("id"), rs.getString("name"),
			            			    new Amount(rs.getDouble("wholesalerPrice")),rs.getBoolean("available"),
			            			    rs.getInt("stock"));
            		inventory.add(product);
            	}
            }
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		
		this.disconnect();
		return inventory;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		this.connect();
		String query = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, available, stock) " +
		        		"SELECT id, name, wholesalerPrice, available, stock " +
		        		"FROM inventory;";
		boolean result = false;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
	        int rowsInserted = ps.executeUpdate();
	        result =  rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		this.disconnect();
		return result;
	}

	@Override
	public void addProduct(Product product) {
		this.connect();
		String query = "INSERT INTO inventory (name, wholesalerPrice, available, stock) " +
		        		"VALUES (?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			
			ps.setString(1,product.getName());

			Amount amount = product.getWholesalerPrice();
			double amountValue = amount.getValue();
    	  	ps.setDouble(2,amountValue);
    	  	
    	  	ps.setBoolean(3,product.isAvailable());
    	  	ps.setInt(4,product.getStock());
			int rowsInserted = ps.executeUpdate();
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		this.disconnect();		
	}

	@Override
	public void updateProduct(Product product) {
		this.connect();
		String query = "update inventory set stock=? where name=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			
			ps.setInt(1,product.getStock());
			ps.setString(2,product.getName());
			ps.executeUpdate();
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		this.disconnect();	
		
	}

	@Override
	public void deleteProduct(int productId) {
		this.connect();
		String query = "delete from inventory where id=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			
			ps.setInt(1,productId);
			ps.executeUpdate();
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		this.disconnect();	
		
	}

}
