public class App {
    public static void main(String[] args) throws Exception {
        ShoppingMall mall = new ShoppingMall("Cariri Shopping", "Avenida Castelo Branco", 4);
        mall.getStores().add(new Store(1, "Americanas", "Varejo", 3));
        mall.getStores().add(new Store(2, "Centauro", "Esporte", 3));
        mall.getStores().add(new Store(3, "Banco do Brasil", "Banco", 1));
        mall.getStores().add(new Store(4, "C&A", "Roupas", 1));
        mall.getStores().add(new Store(5, "Samsung", "Eletrônicos", 4));
        mall.show_entire_mall();
    }
}
