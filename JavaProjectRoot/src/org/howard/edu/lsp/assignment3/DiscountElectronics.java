package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * Step (2): If the original category is "Electronics" (case-insensitive),
 * applies a 10% discount and rounds to two decimals (HALF_UP).
 * For non-Electronics, normalizes price to two decimals (HALF_UP).
 * Mutates the {@link Product} in place.
 */

class DiscountElectronics implements Transformer {
	private static final BigDecimal TEN_PCT = new BigDecimal("0.10");
	/**
	 * Adjusts {@code p}'s price based on {@code p.getOriginalCategory()}.
	 * @param p product to update
	 */

	public void apply(Product p) {
		if ("Electronics".equalsIgnoreCase(p.getOriginalCategory())) {
			BigDecimal discounted = p.getPrice().multiply(BigDecimal.ONE.subtract(TEN_PCT));
			p.setPrice(discounted.setScale(2, RoundingMode.HALF_UP));
		} else {
			p.setPrice(p.getPrice().setScale(2, RoundingMode.HALF_UP));
		}
	}
}
