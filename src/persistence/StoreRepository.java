package persistence;
import java.util.ArrayList;

import business.Store;

public class StoreRepository extends BaseRepository<Store> {
    public ArrayList<Store> get_stores_at_floor(int floor) {
        ArrayList<Store> result = new ArrayList<>();
        for (Store store : this.data) {
            if (store.getFloor_located() == floor) {
                result.add(store);
            }
        }
        return result;
    }
}
