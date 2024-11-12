import business.ShoppingMall;
import business.User;
import presentation.Interface;

public class App {
    public static void main(String[] args) throws Exception {
        ShoppingMall mall = Interface.create_shopping_database();
        
        boolean running = true;
        while(running) {
            User user = Interface.select_User(mall);
            if(user == null) break;

            //user = Interface.select_User(mall);
            
            Interface.options(user, mall);
        }
    }
}
