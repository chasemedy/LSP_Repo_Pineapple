package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

class DiscountElectronics implements Transformer {
	private static final BigDecimal TEN_PCT = new BigDecimal("0.10");

	public void apply(Product p) {
		if ("Electronics".equalsIgnoreCase(p.getOriginalCategory())) {
			BigDecimal discounted = p.getPrice().multiply(BigDecimal.ONE.subtract(TEN_PCT));
			p.setPrice(discounted.setScale(2, RoundingMode.HALF_UP));
		} else {
			p.setPrice(p.getPrice().setScale(2, RoundingMode.HALF_UP));
		}
	}
}
