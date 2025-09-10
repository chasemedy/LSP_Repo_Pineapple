//

package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {
    private static final Path INPUT_PATH = Paths.get("data", "products.csv");
    private static final Path OUTPUT_PATH = Paths.get("data", "transformed_products.csv");
    private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

    private static int rowsRead = 0;
    private static int rowsTransformed = 0;
    private static int rowsSkipped = 0;

    public static void main(String[] args) {
        List<InputRow> inputRows = extract(INPUT_PATH);
        if (inputRows == null) {
            return;
        }

        List<OutputRow> outputs = transform(inputRows);
        boolean wrote = load(outputs, OUTPUT_PATH);

        System.out.println("\n--- Run Summary ---");
        System.out.println("Input path:  " + INPUT_PATH);
        System.out.println("Output path: " + OUTPUT_PATH);
        System.out.println("Rows read:        " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped:     " + rowsSkipped);
        if (!wrote) {
            System.out.println("Note: Output file was not written due to an error.");
        }
    }

    private static List<InputRow> extract(Path inputPath) {
        if (!Files.exists(inputPath)) {
            System.err.println("ERROR: Missing input file: " + inputPath);
            System.err.println("Make sure you run from the project root and have a data/ folder with products.csv.");
            return null;
        }

        List<InputRow> rows = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) {
                return rows;
            }

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                rowsRead++;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    rowsSkipped++;
                    continue;
                }
                try {
                    int productId = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    BigDecimal price = new BigDecimal(parts[2].trim());
                    String category = parts[3].trim();
                    rows.add(new InputRow(productId, name, price, category));
                } catch (Exception parseEx) {
                    rowsSkipped++;
                }
            }
        } catch (IOException ioEx) {
            System.err.println("ERROR: Failed to read input file: " + ioEx.getMessage());
        }

        return rows;
    }

    private static List<OutputRow> transform(List<InputRow> inputs) {
        List<OutputRow> out = new ArrayList<>();
        for (InputRow in : inputs) {
            try {
                String upperName = in.name == null ? "" : in.name.toUpperCase();
                String originalCategory = in.category == null ? "" : in.category;

                BigDecimal finalPrice = in.price;
                if ("Electronics".equalsIgnoreCase(originalCategory)) {
                    finalPrice = applyDiscount(in.price, new BigDecimal("0.10"));
                } else {
                    finalPrice = scaleMoney(finalPrice);
                }

                String finalCategory = originalCategory;
                if ("Electronics".equalsIgnoreCase(originalCategory)
                        && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
                    finalCategory = "Premium Electronics";
                }

                String priceRange = computePriceRange(finalPrice);

                out.add(new OutputRow(in.productId, upperName, finalPrice, finalCategory, priceRange));
                rowsTransformed++;
            } catch (Exception e) {
                rowsSkipped++;
            }
        }
        return out;
    }

    private static boolean load(List<OutputRow> outputs, Path outputPath) {
        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException ignored) {}

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(OUTPUT_HEADER);
            writer.newLine();
            for (OutputRow r : outputs) {
                writer.write(String.format("%d,%s,%s,%s,%s",
                        r.productId,
                        r.name,
                        r.price.toPlainString(),
                        r.category,
                        r.priceRange));
                writer.newLine();
            }
            return true;
        } catch (IOException ioEx) {
            System.err.println("ERROR: Failed to write output file: " + ioEx.getMessage());
            return false;
        }
    }

    private static BigDecimal applyDiscount(BigDecimal price, BigDecimal pct) {
        BigDecimal oneMinus = BigDecimal.ONE.subtract(pct);
        BigDecimal discounted = price.multiply(oneMinus);
        return scaleMoney(discounted);
    }

    private static BigDecimal scaleMoney(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private static String computePriceRange(BigDecimal finalPrice) {
        if (finalPrice.compareTo(new BigDecimal("10.00")) <= 0) {
            return "Low";
        } else if (finalPrice.compareTo(new BigDecimal("100.00")) <= 0) {
            return "Medium";
        } else if (finalPrice.compareTo(new BigDecimal("500.00")) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }

    private static class InputRow {
        final int productId;
        final String name;
        final BigDecimal price;
        final String category;

        InputRow(int productId, String name, BigDecimal price, String category) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    private static class OutputRow {
        final int productId;
        final String name;
        final BigDecimal price;
        final String category;
        final String priceRange;

        OutputRow(int productId, String name, BigDecimal price, String category, String priceRange) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.category = category;
            this.priceRange = priceRange;
        }
    }
}
