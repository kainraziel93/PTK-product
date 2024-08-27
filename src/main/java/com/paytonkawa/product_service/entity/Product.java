package com.paytonkawa.product_service.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="the product should have a name")
	@Size(min=2)
	private String name;
	private String description;
	private int stock;
	@NotBlank(message="you should asign a price to the product")
	private double price;
	private LocalDateTime createdAt;
	public Product() {
		this.createdAt = LocalDateTime.now();
	}
	public void setId(int id) {
		this.id = id;
	}

	public Product(String name, String description, int stock, double price) {
		super();
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.price = price;
		this.createdAt = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	


	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", stock=" + stock + ", price="
				+ price + ", createdAt=" + createdAt + "]";
	}
	
	
}
