public abstract class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private double wallet;
    private String position;

    public void credit(double amount) {
        wallet += amount;
    }

    public boolean validate(String email, String password) {
        return (this.email.equals(email) && this.password.equals(password));
    }
    
    public void buy(Store store, Product product, int quantity) {
        if (store.getProducts().get_by_id(product.getId()) != null) {
            if (wallet >= product.getPrice()) {
                wallet -= product.getPrice();
                store.sell_product(product, quantity);
            }
        }
    }

    public boolean isManager() {
        return this.position.equals("Manager");
    }
}
