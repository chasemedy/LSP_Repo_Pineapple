# Reflection

*What changed:* 
A2 was one big class doing everything. In A3 I split things into a few small classes:
- "CsvIO" handles read/write; "Product" holds row data (including originalCategory).
- Tiny Transforme interface with four steps: NameUppercase, DiscountElectronics, RecategorizePremium, PriceRange.
- App wires it together and prints the summary.

*Why this is more OO (but still simple):*  
- Encapsulation: CSV I/O and product state live in their own classes.  
- Polymorphism: the pipeline is a List<Transformer> applied in order.  
- Single responsibility: each step does one job easier to change a rule without touching the rest.  
- I didn’t add factories or complex patterns—kept it minimal.

*Correctness checks (same as A2):**  
- Normal sample: matches golden output.  
- Empty input: output file has just the header.  
- Missing file: prints a clear error and exits.  
- Boundaries checked at 10.00, 100.00, 500.00.


