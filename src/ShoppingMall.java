public class ShoppingMall {
    private String name;
    private String location;
    private int floors_qt;
    private StoreRepository stores;

    public boolean add_store(Store store) {
        return this.stores.add(store);
    }

    public boolean remove_store(int store_id) {
        return this.stores.remove(store_id);
    }
}
