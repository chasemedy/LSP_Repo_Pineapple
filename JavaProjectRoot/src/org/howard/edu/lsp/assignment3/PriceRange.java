package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

class PriceRange implements Transformer {
	private static final BigDecimal TEN = new BigDecimal("10.00");
	private static final BigDecimal HUNDRED = new BigDecimal("100.00");
	private static final BigDecimal FIVE_HUNDRED = new BigDecimal("500.00");

	public void apply(Product p) {
		BigDecimal price = p.getPrice();
		String range;
		if (price.compareTo(TEN) <= 0) {
			range = "Low";
		} else if (price.compareTo(HUNDRED) <= 0) {
			range = "Medium";
		} else if (price.compareTo(FIVE_HUNDRED) <= 0) {
			range = "High";
		} else {
			range = "Premium";
		}
		p.setPriceRange(range);
	}
}
