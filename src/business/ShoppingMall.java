package business;
import persistence.StoreRepository;
import persistence.UserRepository;

public class ShoppingMall {
    private String name;
    private String location;
    private int floors_qt;
    private int floors_limit;
    private StoreRepository stores;
    private UserRepository users;

    public ShoppingMall (String name, String location, int floors_qt, int floors_limit){
        this.name = name;
        this.location = location;
        this.floors_qt = floors_qt;
        this.floors_limit = floors_limit;
        this.stores = new StoreRepository();
        this.users = new UserRepository();
    }

    public void show_entire_mall() {
        for (int i = 1; i<=this.floors_qt; i++) {
            if (this.stores.get_stores_at_floor(i).isEmpty()) {
                System.out.print("||\n");
            } else {
                for (Store store : stores.get_stores_at_floor(i)) {
                    System.out.print("| " + store.getName());
                }
                System.out.print("|\n");
            }
        }
    }

    public StoreRepository getStores() {
        return stores;
    }

    public UserRepository getUsers() {
        return users;
    }

    public int getFloors_qt() {
        return floors_qt;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getFloors_limit() {
        return floors_limit;
    }

    public void setFloors_limit(int floors_limit) {
        this.floors_limit = floors_limit;
    }
}
