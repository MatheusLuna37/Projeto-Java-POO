public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity_in_stock;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity_in_stock = quantity;
    }

    public void restock(int amount){
        this.quantity_in_stock += amount;
    }

    public void sell(int quantity) {
        if (this.quantity_in_stock >= quantity) {
            this.quantity_in_stock -= quantity;
        } else {
            System.out.println("Quantidade Indisponível");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }
}
