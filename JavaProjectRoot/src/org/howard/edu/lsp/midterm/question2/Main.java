package org.howard.edu.lsp.midterm.question2;


public class Main {
  public static void main(String[] args) {
    System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
    System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
    System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
    System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

    // Demonstrate exception handling
    try {
      System.out.println("Circle radius -2.0 → area = " + AreaCalculator.area(-2.0));
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }

    /*
     * Explanation:
     * Method overloading is the better design here because all methods calculate
     * an area but for different shapes. Using the same name makes the code simple
     * and consistent instead of having separate methods like circleArea() or
     * rectangleArea().
     */
  }
}
