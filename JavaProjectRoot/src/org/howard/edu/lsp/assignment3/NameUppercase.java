package org.howard.edu.lsp.assignment3;
/**
 * Step (1): Converts the product's name to UPPERCASE.
 * Order-sensitive (should run before later steps). Mutates the {@link Product} in place.
 */



class NameUppercase implements Transformer {
	/**
	 * Uppercases {@code p.getName()} (null-safe; uses empty string if null).
	 * @param p product to update
	 */

	public void apply(Product p) {
		String n = p.getName();
		p.setName(n == null ? "" : n.toUpperCase());
	}
}
