package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Product {
	private final int productId;
	private String name;
	private BigDecimal price;
	private String category;
	private String priceRange = "";
	private final String originalCategory;

	Product(int productId, String name, BigDecimal price, String category) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.category = category;
		this.originalCategory = category;
	}

	int getProductId() {
		return productId;
	}

	String getName() {
		return name;
	}

	BigDecimal getPrice() {
		return price;
	}

	String getCategory() {
		return category;
	}

	String getOriginalCategory() {
		return originalCategory;
	}

	String getPriceRange() {
		return priceRange;
	}

	void setName(String name) {
		this.name = name;
	}

	void setPrice(BigDecimal price) {
		this.price = price;
	}

	void setCategory(String category) {
		this.category = category;
	}

	void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	String toCsvRow() {
		BigDecimal rounded = price.setScale(2, RoundingMode.HALF_UP);
		return String.format("%d,%s,%s,%s,%s", productId, name, rounded.toPlainString(), category, priceRange);
	}
}
