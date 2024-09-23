public class ShoppingMall {
    private String name;
    private String location;
    private int floors_qt;
    private StoreRepository stores;
    private UserRepository users;

    public void show_entire_mall() {
        for (int i = 0; i<=this.floors_qt; i++) {
            for (Store store : stores.get_stores_at_floor(i)) {
                System.out.println("| " + store.getName() + " |");
            }
        }
    }

    public void show_floor(int floor) {
        for (Store store : stores.get_stores_at_floor(floor)) {
            System.out.println("| " + store.getName() + " |");
        }
    }
}
