public class Product {
    private int id;
    private String name;
    private float price;
    private int quantity_in_stock;

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

    public float getPrice() {
        return price;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }
}
