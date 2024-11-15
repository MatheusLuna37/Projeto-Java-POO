package persistence;
import business.Product;

public class ProductRepository extends BaseRepository<Product>{
    public boolean remove(int product_id, int quantity) {
        for (Product obj : this.data) {
            try {
                int objId = (int) obj.getClass().getMethod("getId").invoke(obj);
                if (objId == product_id) {
                    if (obj.getQuantity_in_stock() > quantity) {
                        obj.sell(quantity);
                        update(obj);
                    } else if (obj.getQuantity_in_stock() == quantity) {
                        remove(obj.getId());
                    } else {
                        System.out.println("Quantidade em Estoque Insuficiente");
                        return false;
                    }
                    return true;
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        return false;
    }
}
