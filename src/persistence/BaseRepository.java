package persistence;
import java.util.ArrayList;

abstract class BaseRepository<T>{
    protected ArrayList<T> data;

    public BaseRepository(){
        this.data = new ArrayList<>();
    }

    public boolean add(T item) {
        this.data.add(item);
        return true;
    }

    public T get_by_id(int id) {
        for (T obj : this.data) {
            try {
                int objId = (int) obj.getClass().getMethod("getId").invoke(obj);
                if (objId == id) {
                    return obj;
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        return null;
    }

    public T get_by_name(String name) {
        for (T obj: this.data) {
            try {
                String objName = (String) obj.getClass().getMethod("getName").invoke(obj);
                if (objName.equals(name)) {
                    return obj;
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        return null;
    }

    public boolean update(T item) {
        for (T obj : this.data) {
            try {
                int objId = (int) obj.getClass().getMethod("getId").invoke(obj);
                int itemId = (int) obj.getClass().getMethod("getId").invoke(item);
                if (objId == itemId) {
                    this.data.set(this.data.indexOf(obj), item);
                }
                return true;
            } catch (Exception e) {e.printStackTrace();}
        }
        return false;
    }

    public boolean remove(int id) {
        for (T obj : this.data) {
            try {
                int objId = (int) obj.getClass().getMethod("getId").invoke(obj);
                if (objId == id) {
                    this.data.remove(obj);
                    return true;
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        return false;
    }

    public ArrayList<T> list_all() {
        return this.data;
    }
    
    public int get_total_items() {
        return this.data.size();
    }
}
