package presentation;
import business.Employee;
import business.Product;
import business.ShoppingMall;
import business.Store;
import business.User;

import java.util.Scanner;

public class Interface {
    private static Scanner scanner = new Scanner(System.in);
    
    public static User select_User(ShoppingMall mall) {
        User user = null;
        try {

        System.out.println("Criar conta ou logar com uma já existente:");
        System.out.println("1. Criar");
        System.out.println("2. Logar com uma conta existente");
        System.out.println("0. Sair");
        
        int choice = num_input(Messages.invalid_input(), 0, 2);
        if(choice == 0) return null;
        if(choice == 1) {
            return create_user(mall);
        }
        else {
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

            user = mall.getUsers().get_by_id(id);
            System.out.println("Seja Bem-Vindo(a) " + user.getName() + "!");

            return user;
        }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
        }
        return user;
    }

    public static void options(User user, ShoppingMall mall) {
        
        boolean running = true;
        while(running) {

            int options = 4;
            if (user.isManager()) {
                options = 6;
            }
            System.out.println("1. Andar pelo shopping");
            System.out.println("2. Selecionar uma loja");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Adicionar saldo");
            if (user.isManager()) {
                System.out.println("5. Cadastrar nova loja");
                System.out.println("6. Listar suas lojas");
            }
            System.out.println("0. Fechar programa");
            System.out.println("");


            int choice = num_input(Messages.invalid_input(), 0, options);
            switch(choice) {
                case 0: 
                    running = false;
                    limparTerminal();
                    break;
                case 1: 

                    System.out.println("Escolha um andar (1 -> " + mall.getFloors_qt() +")(0. Voltar):");
                    choice = num_input(Messages.invalid_input(), 0, mall.getFloors_qt());
                    if(choice == 0) break;
                    mall.show_floor(choice);
                    break;

                case 2: 
                    
                    System.out.println("Insira o nome da loja (0. Voltar):");
                    String loja = str_input("Loja não encontrada, insira um nome válido.", true, 0);

                    if(loja.equals("0")) break;

                    Store shop = mall.getStores().get_by_name(loja);
                    if(shop == null) {
                        System.out.println("Loja não encontrada.\n");
                        break;
                    };

                    visit_store(shop, user, mall);
                    break;

                case 3:

                    System.out.println("Seu saldo é: " + user.getWallet() + "\n");
                    break;

                case 4:

                    System.out.println("Insira quanto deseja adicionar: ");

                    double val = double_input(Messages.invalid_value());

                    user.credit(val);
                    System.out.println("Valor de " + val + " creditado com sucesso.");
                    System.out.println("Seu saldo atual é: " + user.getWallet() + "\n");
                    break;
                    
                case 5:

                    System.out.println("Qual o nome da loja?");
                    String name = str_input("Nome inválido, insira novamente.", true, 0);

                    System.out.println("Qual a categoria?");
                    String category = str_input("Categoria Inválida, insira novamente.", false, 0);

                    System.out.println("Em qual andar ela ficará localizada?");
                    int floor = num_input(Messages.invalid_input(), 1, mall.getFloors_qt());

                    int id = mall.getStores().get_total_items() + 1;

                    Store store = new Store(id, name, category, floor);
                    mall.getStores().add(store);
                    System.out.println("Loja adicionada com sucesso!");

                    user.setLojas(store);

                    break;
                
                case 6:
                    
                    for(int i : user.getStores()) {
                        System.out.println(mall.getStores().get_by_id(i).getName());
                    }
                    
                case 7: 

            }
        }
        
        
    }


    public static void visit_store(Store store, User user, ShoppingMall mall) {        
        boolean running = true;
        while(running) {

            int options_qt = 2;
            if (user.isManager()) {
                options_qt = 9;
            }

            System.out.println("1. Listar produtos");
            System.out.println("2. Comprar produto");
            
            if (user.isManager() && user.getStores().contains(store.getId())) {
                System.out.println("3. Listar funcionários");
                System.out.println("4. Adicionar Produto");
                System.out.println("5. Selecionar Produto");
                System.out.println("6. Contratar funcionário");
                System.out.println("7. Promover funcionário");
                System.out.println("8. Demitir Funcionário");
                System.out.println("9. Ver relatório de vendas");
            }

            System.out.println("0. Voltar");

            int choice = num_input(Messages.invalid_input(), 0, options_qt);
            
            switch(choice) {
                case 0:
                    running = false;
                    break;
                case 1:

                    System.out.println("|NOME\t\t|PREÇO\t|QUANTIDADE EM ESTOQUE|");
                    for(Product product : store.getProducts().list_all()) {
                        System.out.println("|" + product.getName() + " \t|" + product.getPrice() + " \t|" + product.getQuantity_in_stock()+ "\t");
                    }

                    break;

                    

                case 2: 

                    System.out.println("Insira o produto que deseja comprar: ");

                    String select = str_input(Messages.invalid_string(), true, 0);

                    Product product = store.getProducts().get_by_name(select);

                    if(product == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    System.out.println("Insira a quantidade desejada: ");

                    int qtd = num_input(Messages.invalid_input(), 0, 0);

                    try {
                        user.buy(store, product, qtd);
                        System.out.println("Compra realizada com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 3:

                    System.out.println("|NOME\t\t|ID\t|CARGO|");
                    for (Employee employee: store.getEmployees().list_all()) {
                        System.out.println("|" + employee.getName() + "\t|" + employee.getId() + "\t|" + employee.getPosition() + "|");
                    }

                    break;

                case 4:

                    System.out.println("Insira o nome do produto:");
                    String productName = str_input("Texto inválido, insira novamente.", true, 0);

                    if(store.getProducts().get_by_name(productName) != null) {
                        System.out.println("Este produto já está a venda.");
                        break;
                    }
                    
                    System.out.println("Insira o preço do produto:");
                    double price = double_input(Messages.invalid_value());

                    System.out.println("Insira a quantidade em estoque:");
                    int productQtd = num_input(Messages.invalid_input(), 0, 0);

                    int totalStore = mall.getStores().get_total_items();

                    try {
                        store.getProducts().add(new Product(totalStore + 1, productName, price, productQtd));
                        System.out.println("Produto adicionado com sucesso.");
                    } catch (Exception e) {
                        System.err.println(Messages.inexpected_error());
                    }
                    
                    break;

                case 5:

                    System.out.println("Insira o nome do produto (0. Voltar):");
                    productName = str_input(Messages.invalid_string(), true, 0);

                    try {
                        product = store.getProducts().get_by_name(productName);
                        if(product == null) throw new Exception("Produto não encontrado.");

                        System.out.println("1. Remover produto");
                        System.out.println("2. Repor Estoque");
                        System.out.println("0. Voltar");

                        choice = num_input(Messages.invalid_input(), 0, 2);

                        switch(choice){
                            case 0: break;
                            case 1:
                                store.getProducts().remove(product.getId());
                                break;
                            case 2:
                                System.out.println("Quantidade de unidades do produto:");
                                int quantity = num_input(Messages.invalid_input(), 0, 0);
                                
                                product.restock(quantity);
                                store.getProducts().update(product);
                                System.out.println("Estoque atualizado!");
                                break;
                        }

                    } catch (Exception e) {
                        System.out.println("Produto não encontrado.");
                    }

                    break;
                    
                case 6:

                    System.out.println("Nome Completo do funcionário:");
                    String name = str_input(Messages.invalid_string(), false, 0);

                    if(store.getEmployees().get_by_name(name) != null) {
                        System.out.println("Nomes de funcionários devem ser únicos.");
                        break;
                    }

                    System.out.println("Cargo:");
                    String position = str_input(Messages.invalid_string(), true, 0);

                    System.out.println("Salário a ser pago:");
                    double salary = double_input(Messages.invalid_value());

                    int id = store.getEmployees().get_total_items() + 1;

                    try {
                        Employee employee = new Employee(id, name, position, salary);
                        store.getEmployees().add(employee);
                        System.out.println("Funcionário empregado com sucesso!");
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;
                    
                case 7:
                    
                    System.out.println("|NOME\t\t|ID\t|CARGO|\t|SALÁRIO");
                    for (Employee emp: store.getEmployees().list_all()) {
                        System.out.println("|" + emp.getName() + "\t|" + emp.getId() + "\t|" + emp.getPosition() + "\t|" + emp.getSalary() + "|");
                    }

                    System.out.println("Insira o ID do funcionário:");
                    id = num_input(Messages.invalid_input(), 0, 0);

                    if(store.getEmployees().get_by_id(id) == null) {
                        System.out.println("Funcionário inexistente.");
                        break;
                    }
                    
                    Employee employee = store.getEmployees().get_by_id(id);

                    System.out.println("Novo Cargo:");
                    position = str_input(Messages.invalid_string(), true, 0);

                    System.out.println("Novo salário:");
                    salary = double_input(Messages.invalid_value());

                    if(salary < employee.getSalary()) {
                        System.out.println("Novo salário não pode ser menor que o anterior.");
                        break;
                    }
                    
                    employee.promote(position);
                    employee.update_salary(salary);
                    store.getEmployees().update(employee);

                    break;

                case 8:

                    System.out.println("|NOME\t\t|ID\t|CARGO|\t|SALÁRIO");
                    for (Employee emp: store.getEmployees().list_all()) {
                        System.out.println("|" + emp.getName() + "\t|" + emp.getId() + "\t|" + emp.getPosition() + "\t|" + emp.getSalary() + "|");
                    }

                    System.out.println("Insira o ID do funcionário:");
                    id = num_input(Messages.invalid_input(), 0, 0);

                    if(store.getEmployees().get_by_id(id) == null) {
                        System.out.println("Funcionário inexistente.");
                        break;
                    }

                    try {
                        store.getEmployees().remove(id);
                        System.out.println("Funcionário devidamente demitido.");
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;

                case 9:
                
                    System.out.println("1. Versão simplificada");
                    System.out.println("2. Versão completa");
                    System.out.println("0. Voltar");

                    choice = num_input(Messages.invalid_input(), 0, 2);
                    if(choice == 0) break;

                    try {
                        System.out.println(store.get_sales_report(choice));
    
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

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
        
        mall.getStores().add(new Store(6, "Teste", "Teste", 3));

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


        mall.getUsers().add(new User(1, "Alice Silva", "alice.silva@example.com", "pass1234", "Costumer"));
        mall.getUsers().add(new User(2, "Bruno Costa", "bruno.costa@example.com", "brunoPass21", "Costumer"));
        mall.getUsers().add(new User(3, "Carla Mendes", "carla.mendes@example.com", "carlaSecure45", "Costumer"));
        mall.getUsers().add(new User(4, "Diego Santos", "diego.santos@example.com", "santosPass2024", "Costumer"));
        mall.getUsers().add(new User(5, "Matheus Bezerra", "matheus.bezerra@aluno.ufca.edu.br", "pooUFCA", "Manager"));
        mall.getUsers().add(new User(6, "Guilherme Viana", "guilherme.viana@aluno.ufca.edu.br", "pooUFCA", "Manager"));
        mall.getUsers().add(new User(7, "Teste1", "t", "1234", "Manager"));



        mall.getUsers().get_by_id(7).setLojas(mall.getStores().get_by_id(6));



        return mall;
    }

    private static int num_input(String msg, int min, int max) {
        int input = 0;
        boolean flag = false;
        do{
            try {
                input = scanner.nextInt();
                if(max != min) {
                    if(input < min || input > max) {
                        throw new Exception();
                    }
                }
                flag = true;
            } catch (Exception e) {
                System.out.println(msg);
            }
            scanner.nextLine();
        }
        while(flag == false);

        return input;
    }

    private static String str_input(String msg, boolean num_perm, int min_char) {
        String input = null;
        boolean flag = false;
        do {
            try {
                input = scanner.nextLine().trim();

                if(!num_perm && input.matches(".*\\d.*")) {
                    System.out.println("Números não são permitidos");
                    throw new Exception("Números não são permitidos");
                }
                if(input.length() < min_char) {
                    System.out.println("Número mínimo de caracteres é: " + min_char);
                    throw new Exception("Número mínimo de caracteres é: " + min_char);
                }
                if(input.isEmpty()) throw new Exception();

                flag = true;
            } catch (Exception e) {
                System.out.println(msg);
            }

        }while(flag == false);
        
        return input;
    }

    private static double double_input(String msg) {
        boolean flag = false;
        double val = 0.0;
        do{
            try {
                val = scanner.nextDouble();
                flag = true;
            } catch (Exception e) {
                System.out.println("Por favor, digite um número válido.");
            }
            scanner.nextLine();
        }
        while(flag == false);
        return val;
    }

    private static User create_user(ShoppingMall mall) {
        System.out.println("Criação de usuário:");
        System.out.println("Insira seu nome:");
        String name = str_input("Formato de nome inválido, insira novamente.", true, 3);
        
        System.out.println("Insira seu email:");
        String email = str_input("Formato de email inválido, insira novamente.", true, 0);
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
        String password = str_input("Formato de senha inválido, insira novamente.", true, 4);

        int totalUsers = mall.getUsers().get_total_items();
        User user = new User(totalUsers + 1, name, email, password, "User");
        mall.getUsers().add(user);

        System.out.println("Bem vindo " + name );
        return user;
    }

    public static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}


