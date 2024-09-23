public class ShoppingMall {
    private String name;
    private String location;
    private int floors_qt;
    private StoreRepository stores;
    private UserRepository users;

    public ShoppingMall (String name, String location, int floors_qt){
        this.name = name;
        this.location = location;
        this.floors_qt = floors_qt;
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

    public void show_floor(int floor) {
        if (this.stores.get_stores_at_floor(floor).isEmpty()) {
            System.out.println("||");
        } else {
            for (Store store : stores.get_stores_at_floor(floor)) {
                System.out.println("| " + store.getName() + " |");
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
}
