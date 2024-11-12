package business;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private double wallet;
    private String position;
    private ArrayList<Integer> stores = new ArrayList<>();

    public User(int id, String name, String email, String password, String position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
    }

    public void credit(double amount) {
        wallet += amount;
    }

    public boolean existing_email(String email) {
        return (this.email.equals(email));
    }

    public boolean validate(String email, String password) {
        return (this.email.equals(email) && this.password.equals(password));
    }
    
    public boolean buy(Store store, Product product, int quantity) throws Exception{
        if (store.getProducts().get_by_id(product.getId()) != null) {
            if(quantity > product.getQuantity_in_stock()) throw new Exception("Quantidade em estoque insuficiente");
            if (wallet < product.getPrice() * quantity) throw new Exception("Saldo insuficiente");
            wallet -= product.getPrice() * quantity;
            store.sell_product(product, quantity);
            return true;
        }
        return false;
    }

    public double getWallet() {
        return wallet;
    }
 
    public boolean isManager() {
        return this.position.equals("Manager");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setLojas(Store store) {
        this.stores.add(store.getId());
    }

    public ArrayList<Integer> getStores() {
        return new ArrayList<Integer>(stores);
    }
}
