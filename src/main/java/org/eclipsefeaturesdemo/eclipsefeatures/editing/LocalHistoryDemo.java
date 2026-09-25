package org.eclipsefeaturesdemo.eclipsefeatures.editing;

public class LocalHistoryDemo {
    public static void main(String[] args) {
        String report = generateInventoryReport(24);

        System.out.println(report);
        System.out.println(getProcessingStatus());
    }

    private static String generateInventoryReport(int productCount) {
        return "Inventory report generated for " + productCount + " products";
    }

    private static String getProcessingStatus() {
        return "Inventory processing completed";
    }

}
