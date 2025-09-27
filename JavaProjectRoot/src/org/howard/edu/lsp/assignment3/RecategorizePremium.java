package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
/**
 * Step (3): If the final (post-discount) price > 500.00 and the original
 * category was "Electronics", sets category to "Premium Electronics";
 * otherwise keeps/restores the original category. Mutates in place.
 */

class RecategorizePremium implements Transformer {
	private static final BigDecimal THRESHOLD = new BigDecimal("500.00");
	/**
	 * Updates {@code p}'s category based on final price and original category.
	 * @param p product to update
	 */

	public void apply(Product p) {
		if ("Electronics".equalsIgnoreCase(p.getOriginalCategory()) && p.getPrice().compareTo(THRESHOLD) > 0) {
			p.setCategory("Premium Electronics");
		} else {
			p.setCategory(p.getOriginalCategory());
		}
	}
}
