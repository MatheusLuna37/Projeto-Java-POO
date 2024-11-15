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

        int biggestName = 0;
        for (Product product : this.sales.keySet()) {
            if(product.getName().length() > biggestName) {
                biggestName = product.getName().length();
            }
        }
        biggestName = biggestName > 4 ? biggestName : 4;

        result += ("+-----" + "-".repeat(biggestName - 4) + "-".repeat(14)  + "+\n");
        int tam = ("-----" + "-".repeat(biggestName - 4) + "-".repeat(14)).length();
        int total = ("| Total:" + String.valueOf(this.total_sales)).length();
        System.out.println(tam);
        System.out.println(total);
        result += ("| NOME" + " ".repeat(biggestName - 4) + " |");
        result += (" QUANTIDADE |\n");
        result += ("+-----" + "-".repeat(biggestName - 4) + "-".repeat(14) + "+\n");
        for(Product product : this.sales.keySet()) {
            String nome = product.getName();

            result += ("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

            result += (" " +  this.sales.get(product).toString() + " ".repeat(10 -  this.sales.get(product).toString().length()) + " |\n");

            result += ("+-----" + "-".repeat(biggestName - 4) + "-".repeat(14) + "+\n");
        }

        result += ("| Total: " + String.valueOf(this.total_sales) + " ".repeat(tam > total ? tam - total : 0) + "|\n");
        result += ("+-----" + "-".repeat(biggestName - 4) + "-".repeat(14) + "+\n");
        return result;
    }

    public String generate_simplified_version() {
        String result = "";
        result += "Total em vendas: " + String.valueOf(this.total_sales);
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
