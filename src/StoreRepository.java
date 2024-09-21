import java.util.ArrayList;

public class StoreRepository extends BaseRepository<Store> {
    public ArrayList<Store> get_stores_at_floor(int floor) {
        ArrayList<Store> result = null;
        for (Store store : this.data) {
            if (store.getFloor_located() == floor) {
                result.add(store);
            }
        }
        return result;
    }
}
