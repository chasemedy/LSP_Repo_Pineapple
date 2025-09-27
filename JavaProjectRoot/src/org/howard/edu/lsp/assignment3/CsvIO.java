package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** CSV read/write for products; always writes header and handles empty/missing input. */

class CsvIO {
	private static final String HEADER = "ProductID,Name,Price,Category,PriceRange";

	List<Product> readProducts(Path input, Summary s) {
		if (!Files.exists(input)) {
			System.err.println("ERROR: Missing input file: " + input);
			System.err.println("Make sure you run from the project root and have data/products.csv.");
			return null;
		}

		List<Product> out = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
			String line = br.readLine(); // header
			if (line == null) {
				return out; // empty file; still write header later
			}
			while ((line = br.readLine()) != null) {
				if (line.isBlank())
					continue;
				s.rowsRead++;
				String[] parts = line.split(",", -1);
				if (parts.length != 4) {
					s.rowsSkipped++;
					continue;
				}
				try {
					int id = Integer.parseInt(parts[0].trim());
					String name = parts[1].trim();
					BigDecimal price = new BigDecimal(parts[2].trim());
					String category = parts[3].trim();
					out.add(new Product(id, name, price, category));
				} catch (Exception ex) {
					s.rowsSkipped++;
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: Failed to read input file: " + e.getMessage());
		}
		return out;
	}

	boolean writeProducts(List<Product> items, Path output) {
		try {
			Files.createDirectories(output.getParent());
		} catch (IOException ignored) {
		}

		try (BufferedWriter bw = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
			bw.write(HEADER);
			bw.newLine();
			for (Product p : items) {
				bw.write(p.toCsvRow());
				bw.newLine();
			}
			return true;
		} catch (IOException e) {
			System.err.println("ERROR: Failed to write output file: " + e.getMessage());
			return false;
		}
	}
}
