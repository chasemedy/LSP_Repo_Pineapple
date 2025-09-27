package org.howard.edu.lsp.assignment3;

class NameUppercase implements Transformer {
	public void apply(Product p) {
		String n = p.getName();
		p.setName(n == null ? "" : n.toUpperCase());
	}
}
