package presentation;
import business.Employee;
import business.Product;
import business.ShoppingMall;
import business.Store;
import business.User;
import java.util.Scanner;

public class Interface {

    
    public static User select_User(ShoppingMall mall) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Criar conta ou logar com uma já existente:");
        System.out.println("1. Criar");
        System.out.println("2. Logar com uma conta existente");
        System.out.println("0. Sair");
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
            boolean used = true;
            while (used) {
                used = false;
                for (User u : mall.getUsers().list_all()) {
                    if (u.existing_email(email)) {
                        used = true;
                        break;
                    }
                }
                if (used) {
                    System.out.println("Email já cadastrado, tente outro:");
                    email = scanner.nextLine().trim();
                }
            }

            System.out.println("Insira sua senha:");
            String password = scanner.nextLine().trim();

            int totalUsers = mall.getUsers().get_total_items();
            User user = new User(totalUsers + 1, name, email, password, "User");
            mall.getUsers().add(user);

            System.out.println("Bem vindo " + name );

            return user;
        }
        else {
            scanner.nextLine();
            System.out.println("Digite seu email:");
            String email = scanner.nextLine();
            System.out.println("Digite sua senha:");
            String psw = scanner.nextLine();
            int id = 0;
            boolean valid = false;
            while (!valid) {
                for (User u : mall.getUsers().list_all()) {
                    if (u.validate(email, psw)) {
                        id = u.getId();
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Credenciais inválidas, tente novamente:");
                    System.out.println("Digite seu email:");
                    email = scanner.nextLine();
                    System.out.println("Digite sua senha:");
                    psw = scanner.nextLine();
                }
            }

            User user = mall.getUsers().get_by_id(id);
            System.out.println("Seja Bem-Vindo(a) " + user.getName() + "!");

            return user;
        }
    }

    public static void options(User user, ShoppingMall mall) {
        Scanner scanner = new Scanner(System.in);
        
        
        boolean running = true;
        while(running) {

            int options = 4;
            if (user.isManager()) {
                options = 5;
            }
            System.out.println("1. Andar pelo shopping");
            System.out.println("2. Selecionar uma loja");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Adicionar saldo");
            if (user.isManager()) {
                System.out.println("5. Cadastrar nova loja");
            }
            System.out.println("0. Fechar programa");
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
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.println("Qual o nome da loja?");
                    String name = scanner.nextLine();
                    System.out.println("Qual a categoria?");
                    String category = scanner.nextLine();
                    System.out.println("Em qual andar ela ficará localizada?");
                    int floor = 0;
                    while (true) {
                        try {
                            floor = scanner.nextInt();
                            if (floor <= mall.getFloors_qt() && floor >= 1) {
                                break;
                            }
                        } catch (Exception e) {
                            scanner.nextLine();
                        }
                        out_limit();
                    }
                    int id = mall.getStores().get_total_items() + 1;
                    Store store = new Store(id, name, category, floor);
                    mall.getStores().add(store);
                    System.out.println("Loja adicionada com sucesso!");
                    break;
            }
        }
        
        
        scanner.close();
    }


    public static void visit_store(Store store, User user) {
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        while(running) {

            int options_qt = 3;
            if (user.isManager()) {
                options_qt = 7;
            }
            System.out.println("1. Listar funcionários");
            System.out.println("2. Listar produtos");
            System.out.println("3. Comprar produto");
            if (user.isManager()) {
                System.out.println("4. Contratar funcionário");
                System.out.println("5. Promover funcionário");
                System.out.println("6. Demitir Funcionário");
                System.out.println("7. Reabastecer estoque");
            }
            System.out.println("0. Voltar");
            int choice = scanner.nextInt();
            if(choice == 0) return;
            while(choice > options_qt || choice < 1) {
                out_limit();
                choice = scanner.nextInt();
            }
            switch(choice) {
                case 1:
                    System.out.println("|NOME\t\t|ID\t|CARGO|");
                    for (Employee employee: store.getEmployees().list_all()) {
                        System.out.println("|" + employee.getName() + "\t|" + employee.getId() + "\t|" + employee.getPosition() + "|");
                    }
                    break;
                case 2: 
                    System.out.println("|NOME\t\t|PREÇO\t|QUANTIDADE EM ESTOQUE|");
                    for(Product product : store.getProducts().list_all()) {
                        System.out.println("|" + product.getName() + " \t|" + product.getPrice() + " \t|" + product.getQuantity_in_stock()+ "\t");
                    }
                    break;
                case 3:
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
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.println("Nome do novo funcionário:");
                    String name = scanner.nextLine();
                    System.out.println("Cargo:");
                    String position = scanner.nextLine();
                    System.out.println("Salário a ser pago:");
                    double salary = 0;
                    while (true) {
                        try {
                            salary = scanner.nextDouble();
                            break;
                        } catch (Exception e) {
                            scanner.nextLine();
                        }
                        out_limit();
                    }
                    int id = store.getEmployees().get_total_items()+1;
                    Employee employee = new Employee(id, name, position, salary);
                    store.getEmployees().add(employee);
                    System.out.println("Funcionário empregado com sucesso!");
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.println("Qual o id do beneficiário?");
                    id = 0;
                    while (true) {
                        while (true) {
                            try {
                                id = scanner.nextInt();
                                break;
                            } catch (Exception e) {
                                scanner.nextLine();
                            }
                            out_limit();
                        }
                        employee = store.getEmployees().get_by_id(id);
                        if (employee == null) {
                            System.out.println("Funcionário inexistente");
                        } else {
                            break;
                        }
                    }
                    System.out.println("|NOME\t\t|ID\t|CARGO|\t|SALÁRIO");
                    for (Employee emp: store.getEmployees().list_all()) {
                        System.out.println("|" + emp.getName() + "\t|" + emp.getId() + "\t|" + emp.getPosition() + "\t|" + emp.getSalary() + "|");
                    }
                    scanner.nextLine();
                    System.out.println("Novo Cargo:");
                    position = scanner.nextLine();
                    System.out.println("Novo salário");
                    salary = 0;
                    while (true) {
                        try {
                            salary = scanner.nextDouble();
                            break;
                        } catch (Exception e) {
                            scanner.nextLine();
                        }
                        out_limit();
                    }
                    employee.promote(position);
                    employee.update_salary(salary);
                    store.getEmployees().update(employee);
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.println("Qual o id do funcionário?");
                    id = 0;
                    while (true) {
                        try {
                            id = scanner.nextInt();
                            break;
                        } catch (Exception e) {
                            scanner.nextLine();
                        }
                        out_limit();
                    }
                    if (store.getEmployees().remove(id)) {
                        System.out.println("Funcionário devidamente demitido.");
                    } else {
                        System.out.println("Funcionário inexistente.");
                    }
                    break;
                case 7:
                    scanner.nextLine();
                    System.out.println("Qual o produto?");
                    name = scanner.nextLine();
                    product = store.getProducts().get_by_name(name);
                    if(product == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }
                    System.out.println("Quantidade de unidades do produto:");
                    int quantity = 0;
                    while (true) {
                        try {
                            quantity = scanner.nextInt();
                            break;
                        } catch (Exception e) {
                            scanner.nextLine();
                        }
                        out_limit();
                    }
                    product.restock(quantity);
                    store.getProducts().update(product);
                    System.out.println("Estoque atualizado!");
                    break;

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
        mall.getUsers().add(new User(2, "Bruno Costa", "bruno.costa@example.com", "brunoPass21", "Costumer"));
        mall.getUsers().add(new User(3, "Carla Mendes", "carla.mendes@example.com", "carlaSecure45", "Costumer"));
        mall.getUsers().add(new User(4, "Diego Santos", "diego.santos@example.com", "santosPass2024", "Manager"));
        mall.getUsers().add(new User(5, "Elena Rocha", "elena.rocha@example.com", "elenaPwd123", "Costumer"));
        mall.getUsers().add(new User(6, "Fabio Lima", "fabio.lima@example.com", "limaFabio77", "Costumer"));



        return mall;
    }

    private static void out_limit() {
        System.out.println("Valor inválido, insira novamente");
    }
}
