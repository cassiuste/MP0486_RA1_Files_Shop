package dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;


import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {
	
	MongoCollection<Document> collection;
	private MongoDatabase mongoDatabase;
	
	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		mongoDatabase = mongoClient.getDatabase("shop");
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		this.connect();
		collection = mongoDatabase.getCollection("inventory");
		ArrayList<Product> inventory = new ArrayList<Product>();		
		for (Document document : collection.find()) {
			Document amountObject = (Document) document.get("wholesalerPrice");
			
			Number value = (Number) amountObject.get("value");
			Amount wholesalerPrice = new Amount(value.doubleValue());
			
			
			int id = document.getInteger("id");
			String name = document.getString("name");
			int stock = document.getInteger("stock");
			
			Product product = new Product(id, name, wholesalerPrice, true, stock);
			inventory.add(product);
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
		this.connect();
		collection = mongoDatabase.getCollection("users");
		Employee employee = null;
		Document document = collection.find(
		        and(eq("id", employeeId), eq("password", password))
				).first();;
		if (document != null) {
			employee = new Employee(document.getInteger("id"), document.getString("name"), document.getString("password"));			
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
