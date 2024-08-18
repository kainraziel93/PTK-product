package com.paytonkawa.product_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paytonkawa.product_service.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
