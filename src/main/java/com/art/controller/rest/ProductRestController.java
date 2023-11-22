package com.art.controller.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ProductDAO;
import com.art.dto.product.ProductDTO;
import com.art.mapper.ProductMapper;
import com.art.models.product.Product;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class ProductRestController {
	@Autowired
	ProductDAO proDAO;

	@GetMapping("/product")
	public ResponseEntity<List<ProductDTO>> getProducts() {
		List<Product> products = proDAO.findAll();
		List<ProductDTO> productDTOs = products.stream().map(ProductMapper::convertToDto).collect(Collectors.toList());
		return ResponseEntity.ok(productDTOs);
	}

	@GetMapping("/product-today")
	public ResponseEntity<List<ProductDTO>> getProductsToday() {
		List<Product> products = proDAO.findByAvailable(true);
		Collections.shuffle(products);

		List<Product> randomProducts = products.stream().limit(24).collect(Collectors.toList());

		List<ProductDTO> productDTOs = randomProducts.stream().map(ProductMapper::convertToDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(productDTOs);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") String key) {
		if (!proDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		Product product = proDAO.findById(key).get();
		ProductDTO productDTO = ProductMapper.convertToDto(product);

		return ResponseEntity.ok(productDTO);
	}

}
