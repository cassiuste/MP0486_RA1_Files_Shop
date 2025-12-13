package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="historical_inventory")
public class ProductHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = true)
	private int id;
    
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column
	private String name;
	
	@Column
    private double price;

    @Column
    private boolean available;
    
    @Column
    private int stock;
    
    public ProductHistory() {}
    
    public ProductHistory(Product product) {
    	this.product = product;
    	this.name = product.getName();
    	this.createdAt = new Date();
    	this.price = product.getPrice();
    	this.available = product.isAvailable();
    	this.stock = product.getStock();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
    
    
}
