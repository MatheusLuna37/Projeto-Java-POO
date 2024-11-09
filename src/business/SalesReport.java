package business;
import java.util.HashMap;

public class SalesReport {
    private HashMap<Product, Integer> sales;
    private double total_sales;

    public SalesReport() {
        this.sales = new HashMap<>();
        this.total_sales = 0;
    }

    public String generate_complete_version() {
        String result = "";
        for (Product product : this.sales.keySet()) {
            result += product.getName();
            result += ": ";
            result += this.sales.get(product).toString() + "\n";
        }
        result += "Total em vendas: " + String.valueOf(this.total_sales) + "\n";
        return result;
    }

    public String generate_simplified_version() {
        String result = "";
        for (Product product : this.sales.keySet()) {
            result += this.sales.get(product).toString() + "\n";
        }
        result += "\nTotal em vendas: " + String.valueOf(this.total_sales) + "\n";
        return result;
    }

    public double getTotal_sales() {
        return total_sales;
    }

    public void register_sale(Product product, Integer quantity) {
        if (this.sales.get(product) == null) {
            this.sales.put(product, quantity);
        } else {
            this.sales.put(product, this.sales.get(product)+quantity);
        }
        this.total_sales += product.getPrice() * quantity;
    }
}
