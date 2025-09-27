package org.howard.edu.lsp.assignment3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** Entry point: runs the A3 OO ETL with same behavior as A2. */


public class App {
	private static final Path INPUT = Paths.get("data", "products.csv");
	private static final Path OUTPUT = Paths.get("data", "transformed_products.csv");

	public static void main(String[] args) {
		Summary s = new Summary();
		CsvIO io = new CsvIO();

		List<Product> rows = io.readProducts(INPUT, s);
		if (rows == null) { // missing file already reported
			return;
		}

		// simple pipeline in the required order
		List<Transformer> steps = new ArrayList<>();
		steps.add(new NameUppercase());
		steps.add(new DiscountElectronics());
		steps.add(new RecategorizePremium());
		steps.add(new PriceRange());

		for (Product p : rows) {
			for (Transformer t : steps) {
				t.apply(p);
			}
			s.rowsTransformed++;
		}

		boolean wrote = io.writeProducts(rows, OUTPUT);

		System.out.println("\n--- Run Summary ---");
		System.out.println("Input path:  " + INPUT);
		System.out.println("Output path: " + OUTPUT);
		System.out.println("Rows read:        " + s.rowsRead);
		System.out.println("Rows transformed: " + s.rowsTransformed);
		System.out.println("Rows skipped:     " + s.rowsSkipped);
		if (!wrote) {
			System.out.println("Note: Output file was not written due to an error.");
		}
	}
}
