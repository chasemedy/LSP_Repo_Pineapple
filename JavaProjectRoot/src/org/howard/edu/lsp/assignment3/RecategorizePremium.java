package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

class RecategorizePremium implements Transformer {
	private static final BigDecimal THRESHOLD = new BigDecimal("500.00");

	public void apply(Product p) {
		if ("Electronics".equalsIgnoreCase(p.getOriginalCategory()) && p.getPrice().compareTo(THRESHOLD) > 0) {
			p.setCategory("Premium Electronics");
		} else {
			p.setCategory(p.getOriginalCategory());
		}
	}
}
