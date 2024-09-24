//import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        ShoppingMall mall = Interface.create_shopping_database();
        
        boolean running = true;
        while(running) {
            User user = Interface.select_User(mall);
            if(user == null) break;
            
            Interface.options(user, mall);
        }
    }
}
