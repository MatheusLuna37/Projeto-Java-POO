public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private double wallet;
    private String position;

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

    public boolean validate(String email, String password) {
        return (this.email.equals(email) && this.password.equals(password));
    }
    
    public boolean buy(Store store, Product product, int quantity) {
        if (store.getProducts().get_by_id(product.getId()) != null) {
            if (wallet >= product.getPrice() * quantity) {
                wallet -= product.getPrice() * quantity;
                store.sell_product(product, quantity);
                return true;
            }
        }
        return false;
    }

    public double getWallet() {
        return wallet;
    }
 
    public boolean isManager() {
        return this.position.equals("Manager");
    }

    public String getEmail() {
        return email;
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
}
