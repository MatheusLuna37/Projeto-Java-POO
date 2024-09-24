import java.util.Scanner;

public class Interface {

    
    public static User select_User(ShoppingMall mall) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("""
        Criar conta ou logar com uma já existente:
        1. Criar
        2. Logar com uma conta existente
        0. Sair
        """);
        int choice = scanner.nextInt();
        if(choice == 0) return null;
        while(choice > 2 || choice < 1) {
            out_limit();
            choice = scanner.nextInt();
        }
        if(choice == 1) {
            System.out.println("Criação de usuário:");
            System.out.println("Insira seu nome:");
            scanner.nextLine();
            String name = scanner.nextLine().trim();
            
            System.out.println("Insira seu email:");
            String email = scanner.nextLine().trim();

            System.out.println("Insira sua senha:");
            String password = scanner.nextLine().trim();

            int totalUsers = mall.getUsers().get_total_items();
            User user = new User(totalUsers + 1, name, email, password, "User");
            mall.getUsers().add(user);

            System.out.println("Bem vindo " + name );

            return user;
        }
        else {
            System.out.println("Escolha o seu usuário:\n");

            for(User user : mall.getUsers().list_all()) {
                System.out.println(user.getId() + ". |" + user.getName() + " \t|" + user.getEmail() + " \t|" + user.getPosition()+ "\t");
            }

            System.out.println("");
            choice = scanner.nextInt();
            while(choice > mall.getUsers().get_total_items() || choice < 1) {
                out_limit();
                choice = scanner.nextInt();
            }

            return mall.getUsers().get_by_id(choice);
        }
    }

    public static void options(User user, ShoppingMall mall) {
        Scanner scanner = new Scanner(System.in);
        
        
        boolean running = true;
        while(running) {

            int options = 4;
            System.out.print("""
            1. Andar pelo shopping
            2. Selecionar uma loja
            3. Consultar saldo
            4. Adicionar saldo
            0. Fechar programa
            """);
            System.out.println("");
            int choice = scanner.nextInt();
            if(choice == 0) return;
            while(choice > options || choice < 1) {
                out_limit();
                choice = scanner.nextInt();
            }

            switch(choice) {
                case 0: 
                    break;
                case 1: 

                    System.out.println("Escolha um andar (1 -> " + mall.getFloors_qt() +")(0. Voltar):");
                    choice = scanner.nextInt();
                    if(choice == 0) break;
                    while(choice > mall.getFloors_qt() || choice < 1) {
                        out_limit();
                        choice = scanner.nextInt();
                    }
                    mall.show_floor(choice);
                    break;

                case 2: 
                    System.out.println("Insira o nome da loja (0. Voltar):");
                    scanner.nextLine();
                    String loja = scanner.nextLine();
                    if(loja.equals("0")) break;

                    Store shop = mall.getStores().get_by_name(loja);
                    if(shop == null) {
                        System.out.println("Loja não encontrada.\n");
                        break;
                    };

                    visit_store(shop, user);
                    break;

                case 3:
                    System.out.println("Seu saldo é: " + user.getWallet() + "\n");
                    break;
                case 4:
                    System.out.println("Insira quanto deseja adicionar: ");
                    double val = scanner.nextDouble();
                    user.credit(val);
                    System.out.println("Valor de " + val + " creditado com sucesso.");
                    System.out.println("Seu saldo atual é: " + user.getWallet() + "\n");
                
            }
        }
        
        
        scanner.close();
    }


    public static void visit_store(Store store, User user) {
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        while(running) {

            System.out.println("""
            1. Listar produtos
            2. Comprar produto
            """);
            int choice = scanner.nextInt();
            if(choice == 0) return;
            while(choice > 2 || choice < 1) {
                out_limit();
                choice = scanner.nextInt();
            }
            switch(choice) {
                case 1: 
                    System.out.println("|NOME\t\t|PRECO\t|QUANTIDADE EM ESTOQUE|");
                    for(Product product : store.getProducts().list_all()) {
                        System.out.println("|" + product.getName() + " \t|" + product.getPrice() + " \t|" + product.getQuantity_in_stock()+ "\t");
                    }
                    break;
                case 2:
                    System.out.println("Insira o produto que deseja comprar: ");
                    scanner.nextLine();
                    String select = scanner.nextLine().trim();
                    Product product = store.getProducts().get_by_name(select);
                    if(product == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }
                    System.out.println("Insira a quantidade desejada: ");
                    int qtd = scanner.nextInt();
                    if(user.buy(store, product, qtd)){
                        System.out.println("Compra realizada com sucesso.");
                    }
                    else {
                        System.out.println("Saldo insuficiente.");
                        break;
                    }
            }
        }
    }

    public static ShoppingMall create_shopping_database() {
        ShoppingMall mall = new ShoppingMall("Cariri Shopping", "Avenida Castelo Branco", 4);
        mall.getStores().add(new Store(1, "Americanas", "Varejo", 3));
        mall.getStores().add(new Store(2, "Centauro", "Esporte", 3));
        mall.getStores().add(new Store(3, "Banco do Brasil", "Banco", 1));
        mall.getStores().add(new Store(4, "C&A", "Roupas", 1));
        mall.getStores().add(new Store(5, "Samsung", "Eletrônicos", 4));

        for(Store store : mall.getStores().list_all()) {
            for(int i=1; i<6; i++) {
                store.getProducts().add(new Product(i, "Produto" + i, 12.4, 20));
            }
        }

        for(Store store : mall.getStores().list_all()) {
            for(int i=1; i<4; i++) {
                store.getEmployees().add(new Employee(i, "Empregado" + i, "Vendedor", 1200.5));
            }
        }


        mall.getUsers().add(new User(1, "Alice Silva", "alice.silva@example.com", "pass1234", "Manager"));
        mall.getUsers().add(new User(2, "Bruno Costa", "bruno.costa@example.com", "brunoPass21", "User"));
        mall.getUsers().add(new User(3, "Carla Mendes", "carla.mendes@example.com", "carlaSecure45", "User"));
        mall.getUsers().add(new User(4, "Diego Santos", "diego.santos@example.com", "santosPass2024", "Manager"));
        mall.getUsers().add(new User(5, "Elena Rocha", "elena.rocha@example.com", "elenaPwd123", "User"));
        mall.getUsers().add(new User(6, "Fabio Lima", "fabio.lima@example.com", "limaFabio77", "User"));



        return mall;
    }

    private static void out_limit() {
        System.out.println("Valor inválido, insira novamente");
    }
}
