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

            System.out.println("""
            +--------------------------------------------+
            |Criar conta ou logar com uma conta existente|
            +--------------------------------------------+
            |    1. Criar                                |
            |    2. Logar com uma conta existente        |
            +--------------------------------------------+
            |    0. Sair                                 |
            +--------------------------------------------+""");
            
            int choice = num_input(Messages.invalid_input(), 0, 2);
            if(choice == 0) return null;
            if(choice == 1) {
                return create_user(mall);
            }
            else {

                table("Login");

                System.out.println("\nDigite seu email:");
                String email = scanner.nextLine();

                System.out.println("\nDigite sua senha:");
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
                        System.out.println("\nDigite seu email:");
                        email = scanner.nextLine();
                        System.out.println("\nDigite sua senha:");
                        psw = scanner.nextLine();
                    }
                }

                user = mall.getUsers().get_by_id(id);
                System.out.println("");
                table("Seja Bem-Vindo(a) " + user.getName() + "!");
                wait(2);
                limparTerminal();

                return user;
        }
        } catch (Exception e) {
            System.out.println(Messages.inexpected_error());
        }
        return user;
    }

    public static void options(User user, ShoppingMall mall) {
        
        boolean running = true;
        while(running) {

            int options = 4;
            if (user.isManager()) options = 6;

            System.out.println("""
                +------------------------------+
                |    Opções                    |
                +------------------------------+
                |    1. Andar pelo shopping    |
                |    2. Selecionar uma loja    |
                |    3. Consultar saldo        |
                |    4. Adicionar saldo        |""");
            if (user.isManager()) {
                System.out.println("""
                |    5. Cadastrar nova loja    |
                |    6. Listar suas lojas      |""");
            }
            System.out.println("""
                +------------------------------+
                |    0. Sair                   |
                +------------------------------+""");

            int choice = num_input(Messages.invalid_input(), 0, options);
            switch(choice) {
                case 0: 
                    running = false;
                    limparTerminal();
                    break;
                case 1: 

                    table("Selecione um andar [1 -> " + mall.getFloors_qt() + "]");
                    System.out.println("(0. Voltar)");

                    choice = num_input(Messages.invalid_input(), 0, mall.getFloors_qt());
                    if(choice == 0) break;

                    if (mall.getStores().get_stores_at_floor(choice).isEmpty()) {
                        table("[Andar vazio]");
                    } 
                    else {


                        String biggestName = "";
                        for (Store store : mall.getStores().get_stores_at_floor(choice)) {
                            if(store.getName().length() > biggestName.length()) {
                                biggestName = store.getName();
                            }
                        }
                        System.out.println("+" + "-".repeat(biggestName.length()) + "--+");
                        for (Store store : mall.getStores().get_stores_at_floor(choice)) {
                            System.out.println("| " + store.getName() + " ".repeat(biggestName.length() - store.getName().length()) + " |");
                            System.out.println("+" + "-".repeat(biggestName.length()) + "--+");
                        }
                        wait(2);
                    }

                    break;

                case 2: 
                    
                    table("Insira o nome da loja");
                    System.out.println("(0. Voltar)");

                    String loja = str_input(Messages.invalid_string(), true, 0);

                    if(loja.equals("0")) break;

                    Store shop = mall.getStores().get_by_name(loja);
                    if(shop == null) {
                        table("Loja não encontrada.");
                        wait(2);
                        limparTerminal();
                        break;
                    };
                    limparTerminal();
                    visit_store(shop, user, mall);
                    break;

                case 3:

                    table("Seu saldo é: " + user.getWallet());
                    break;

                case 4:
                    
                    table("POO-Bank");
                    
                    System.out.println("\nInsira quanto deseja adicionar: ");

                    double val = double_input(Messages.invalid_value());

                    user.credit(val);
                    System.out.println("\nValor de " + val + " creditado com sucesso.");
                    table("Seu saldo atual é: " + user.getWallet());
                    wait(2);
                    limparTerminal();
                    break;
                    
                case 5:

                    table("Criação de Loja");

                    System.out.println("\nQual o nome da loja?");
                    String name = str_input("Nome inválido, insira novamente.", true, 0);

                    System.out.println("\nQual a categoria?");
                    String category = str_input("Categoria Inválida, insira novamente.", false, 0);

                    System.out.println("\nEm qual andar ela ficará localizada?");
                    int floor = num_input(Messages.invalid_input(), 1, mall.getFloors_qt());

                    boolean flag = true;
                    do {
                        if(mall.getStores().get_stores_at_floor(floor).size() >= mall.getFloors_limit()) {
                            System.out.println("Este andar está lotado, insira outro andar");
                            floor = num_input(Messages.invalid_input(), 1, mall.getFloors_qt());
                            break;
                        }
                        flag = false;
                    }while(flag);

                    int id = mall.getStores().get_total_items() + 1;

                    try {
                        Store store = new Store(id, name, category, floor);
                        mall.getStores().add(store);
                        user.setLojas(store);
                        table("Loja " + name + " adicionada com sucesso!");
                        wait(2);
                        limparTerminal();
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;
                
                case 6:
                    
                    String biggestName = "";
                    for (int i : user.getStores()) {
                        if(mall.getStores().get_by_id(i).getName().length() > biggestName.length()) {
                            biggestName = mall.getStores().get_by_id(i).getName();
                        }
                    }
                    System.out.println("+" + "-".repeat(biggestName.length()) + "--+");
                    for(int i : user.getStores()) {
                        String nome = mall.getStores().get_by_id(i).getName();
                        System.out.println("| " + nome + " ".repeat(biggestName.length() - nome.length()) + " |");
                            System.out.println("+" + "-".repeat(biggestName.length()) + "--+");
                    }
                    wait(2);
                    break;

            }
        }
        
        
    }


    public static void visit_store(Store store, User user, ShoppingMall mall) {        
        boolean running = true;
        while(running) {

            int options_qt = 1;
            if (user.isManager()) {
                options_qt = 8;
            }

            System.out.println("""
                +--------------------------------+
                |    Opções                      |
                +--------------------------------+
                |    1. Comprar produto          |""");
            
            if (user.isManager() && user.getStores().contains(store.getId())) {
                System.out.println("""
                |    2. Listar funcionários      |
                |    3. Adicionar Produto        |
                |    4. Selecionar Produto       |
                |    5. Contratar Funcionário    |
                |    6. Promover Funcionário     |
                |    7. Demitir Funcionário      |
                |    8. Relatório de Vendas      |""");
            }

            System.out.println("""
                +--------------------------------+
                |    0. Voltar                   |
                +--------------------------------+""");

            int choice = num_input(Messages.invalid_input(), 0, options_qt);
            
            switch(choice) {
                case 0:
                    running = false;
                    break;
                case 1:


                    int biggestName = 0;
                    for (Product product : store.getProducts().list_all()) {
                        if(product.getName().length() > biggestName) {
                            biggestName = product.getName().length();
                        }
                    }
                    biggestName = biggestName > 4 ? biggestName : 4;
                    String text = " | PREÇO     | QUANTIDADE EM ESTOQUE |";
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1)  + "+");
                    System.out.println("| NOME" + " ".repeat(biggestName - 4) + text);
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1)  + "+");
                    for(Product product : store.getProducts().list_all()) {
                        String nome = product.getName();

                        System.out.print("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

                        System.out.print(" " + product.getPrice() + " ".repeat(9 - String.valueOf(product.getPrice()).length()) + " |" + " " + product.getQuantity_in_stock() + " ".repeat(22 - Integer.toString(product.getQuantity_in_stock()).length()) + "|\n");
                        System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1) + "+");
                    }
                    if(store.getProducts().list_all().isEmpty()) {
                        break;
                    }

                    table("Compra de Produtos");

                    System.out.println("\nInsira o produto que deseja comprar: ");
                    System.out.println("(0. Voltar)");

                    String select = str_input(Messages.invalid_string(), true, 0);

                    if(select.equals("0")) {
                        limparTerminal();
                        break;
                    }

                    Product product = store.getProducts().get_by_name(select);

                    if(product == null) {
                        table("Produto não encontrado.");
                        wait(2);
                        limparTerminal();
                        break;
                    }

                    System.out.println("\nInsira a quantidade desejada: ");

                    int qtd = num_input(Messages.invalid_input(), 0, 0);

                    try {
                        user.buy(store, product, qtd);
                        table("Compra realizada com sucesso.");
                    } catch (Exception e) {
                        table(e.getMessage());
                    }
                    wait(2);

                    break;              

                case 2:

                    biggestName = 0;
                    int biggestPosition = 0;
                    for (Employee employee : store.getEmployees().list_all()) {
                        if(employee.getName().length() > biggestName) {
                            biggestName = employee.getName().length();
                        }
                        if(employee.getPosition().length() > biggestPosition) {
                            biggestPosition = employee.getPosition().length();
                        }
                    }

                    biggestName = biggestName > 4 ? biggestName : 4;
                    biggestPosition = biggestPosition > 5 ?biggestPosition : 5;
                    text = " | ID     | ";
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length())  + "-".repeat(biggestPosition) + "-+");
                    System.out.print("| NOME" + " ".repeat(biggestName - 4) + text);
                    System.out.println("CARGO" + " ".repeat(biggestPosition - 5) + " |");
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length())  + "-".repeat(biggestPosition) + "-+");
                    for(Employee employee : store.getEmployees().list_all()) {
                        String nome = employee.getName();
                        String postion = employee.getPosition();

                        System.out.print("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

                        System.out.print(" " + employee.getId() + " ".repeat(6 - String.valueOf(employee.getId()).length()) + " |");

                        System.out.print( " " + employee.getPosition() + " ".repeat(biggestPosition >= postion.length() ?biggestPosition - postion.length() : 4 - biggestPosition) + " |\n");

                        System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length()) + "-".repeat(biggestPosition) + "-+");
                    }
                    if(store.getEmployees().list_all().isEmpty()) {
                        break;
                    }

                    break;

                case 3:

                    table("Adição de Produto");

                    System.out.println("\nInsira o nome do produto:");
                    String productName = str_input(Messages.invalid_string(), false, 0);

                    if(store.getProducts().get_by_name(productName) != null) {
                        table("Este produto já está a venda.");
                        wait(2);
                        limparTerminal();
                        break;
                    }
                    
                    System.out.println("\nInsira o preço do produto:");
                    double price = double_input(Messages.invalid_value());

                    System.out.println("\nInsira a quantidade em estoque:");
                    int productQtd = num_input(Messages.invalid_input(), 0, 0);

                    int totalStore = mall.getStores().get_total_items();

                    try {
                        store.getProducts().add(new Product(totalStore + 1, productName, price, productQtd));
                        table("Produto adicionado com sucesso.");
                    } catch (Exception e) {
                        table(Messages.inexpected_error());
                    }
                    wait(2);
                    limparTerminal();
                    
                    break;

                case 4:

                    biggestName = 0;
                    for (Product p : store.getProducts().list_all()) {
                        if(p.getName().length() > biggestName) {
                            biggestName = p.getName().length();
                        }
                    }
                    biggestName = biggestName > 4 ? biggestName + 4 : 4;
                    text = " | PREÇO     | QUANTIDADE EM ESTOQUE |";
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1)  + "+");
                    System.out.println("| NOME" + " ".repeat(biggestName - 4) + text);
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1)  + "+");
                    for(Product p : store.getProducts().list_all()) {
                        String nome = p.getName();

                        System.out.print("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

                        System.out.print(" " + p.getPrice() + " ".repeat(9 - String.valueOf(p.getPrice()).length()) + " |" + " " + p.getQuantity_in_stock() + " ".repeat(22 - Integer.toString(p.getQuantity_in_stock()).length()) + "|\n");
                        System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() -1) + "+");
                    }
                    if(store.getProducts().list_all().isEmpty()) {
                        break;
                    }

                    System.out.println("Insira o nome do produto:");
                    System.out.println("(0. Voltar)");

                    productName = str_input(Messages.invalid_string(), true, 0);

                    try {
                        product = store.getProducts().get_by_name(productName);
                        if(product == null) throw new Exception("Produto não encontrado.");

                        System.out.println("""
                            +--------------------------------+
                            |    Opções                      |
                            +--------------------------------+
                            |    1. Remover produto          |
                            |    2. Repor Estoque            |""");

                        System.out.println("""
                            +--------------------------------+
                            |    0. Voltar                   |
                            +--------------------------------+""");

                        choice = num_input(Messages.invalid_input(), 0, 2);

                        switch(choice){
                            case 0: break;
                            case 1:
                                try {
                                    store.getProducts().remove(product.getId());
                                    table("Produto Removido.");
                                } catch (Exception e) {
                                    System.out.println(Messages.inexpected_error());
                                }
                                wait(2);
                                limparTerminal();
                                break;
                            case 2:
                                System.out.println("Quantidade de unidades do produto:");
                                System.out.println("(0. Voltar)");

                                int quantity = num_input(Messages.invalid_input(), 0, 0);
                                
                                if(quantity == 0) {
                                    limparTerminal();
                                    break;
                                }

                                product.restock(quantity);
                                store.getProducts().update(product);
                                table("Estoque atualizado!");
                                wait(2);
                                limparTerminal();
                                break;
                        }

                    } catch (Exception e) {
                        System.out.println("Produto não encontrado.");
                    }

                    break;
                    
                case 5:

                    table("Contratação");

                    System.out.println("\nNome Completo do funcionário:");
                    String name = str_input(Messages.invalid_string(), false, 0);

                    if(store.getEmployees().get_by_name(name) != null) {
                        table("Nomes de funcionários devem ser únicos.");
                        wait(2);
                        break;
                    }

                    System.out.println("\nCargo:");
                    String position = str_input(Messages.invalid_string(), true, 0);

                    System.out.println("\nSalário:");
                    double salary = double_input(Messages.invalid_value());

                    int id = store.getEmployees().get_total_items() + 1;

                    try {
                        Employee employee = new Employee(id, name, position, salary);
                        store.getEmployees().add(employee);
                        table("Funcionário empregado com sucesso!");
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }
                    
                    wait(2);
                    limparTerminal();
                    break;
                    
                case 6:

                    biggestName = 0;
                    biggestPosition = 0;
                    for (Employee employee : store.getEmployees().list_all()) {
                        if(employee.getName().length() > biggestName) {
                            biggestName = employee.getName().length();
                        }
                        if(employee.getPosition().length() > biggestPosition) {
                            biggestPosition = employee.getPosition().length();
                        }
                    }

                    biggestName = biggestName > 4 ? biggestName : 4;
                    text = " | ID     | ";
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12)  + "-".repeat(biggestPosition) + "-+");
                    System.out.print("| NOME" + " ".repeat(biggestName - 4) + text);
                    System.out.println("CARGO" + " ".repeat(biggestPosition - 5) + " | SALARIO   |");
                    
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12)  + "-".repeat(biggestPosition) + "-+");
                    for(Employee employee : store.getEmployees().list_all()) {
                        String nome = employee.getName();
                        String postion = employee.getPosition();

                        System.out.print("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

                        System.out.print(" " + employee.getId() + " ".repeat(6 - String.valueOf(employee.getId()).length()) + " |");

                        System.out.print( " " + employee.getPosition() + " ".repeat(biggestPosition >= postion.length() ?biggestPosition - postion.length() : 4 - biggestPosition) + " | ");

                        System.out.print(employee.getSalary() + " ".repeat(9 - String.valueOf(employee.getSalary()).length()) + " |\n");

                        System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12) + "-".repeat(biggestPosition) + "-+");


                    }
                    if(store.getEmployees().list_all().isEmpty()) {
                        break;
                    }

                    System.out.println("\nInsira o ID do funcionário:");
                    System.out.println("(0. Voltar)");
                    id = num_input(Messages.invalid_input(), 0, 0);

                    if(id == 0) {
                        limparTerminal();
                        break;
                    }

                    if(store.getEmployees().get_by_id(id) == null) {
                        table("Funcionário inexistente.");
                        wait(2);
                        limparTerminal();
                        break;
                    }
                    
                    try {
                        Employee employee = store.getEmployees().get_by_id(id);
                        table("Atualização de Cargo");

                        System.out.println("\nNovo Cargo:");
                        position = str_input(Messages.invalid_string(), true, 0);

                        System.out.println("\nNovo salário:");
                        salary = double_input(Messages.invalid_value());

                        if(salary < employee.getSalary()) {
                            table("Novo salário não pode ser menor que o anterior.");
                            wait(2);
                            limparTerminal();
                            break;
                        }
                        
                        employee.promote(position);
                        employee.update_salary(salary);
                        store.getEmployees().update(employee);

                        table("Cargo atualizado com sucesso.");

                        wait(2);
                        limparTerminal();
                        break;

                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;

                case 7:

                    biggestName = 0;
                    biggestPosition = 0;
                    for (Employee employee : store.getEmployees().list_all()) {
                        if(employee.getName().length() > biggestName) {
                            biggestName = employee.getName().length();
                        }
                        if(employee.getPosition().length() > biggestPosition) {
                            biggestPosition = employee.getPosition().length();
                        }
                    }

                    biggestName = biggestName > 4 ? biggestName : 4;
                    text = " | ID     | ";
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12)  + "-".repeat(biggestPosition) + "-+");
                    System.out.print("| NOME" + " ".repeat(biggestName - 4) + text);
                    System.out.println("CARGO" + " ".repeat(biggestPosition - 5) + " | SALARIO   |");
                    
                    System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12)  + "-".repeat(biggestPosition) + "-+");
                    for(Employee employee : store.getEmployees().list_all()) {
                        String nome = employee.getName();
                        String postion = employee.getPosition();

                        System.out.print("| " + nome + " ".repeat(biggestName >= nome.length() ? biggestName - nome.length() : 3 - biggestName) + " |");

                        System.out.print(" " + employee.getId() + " ".repeat(6 - String.valueOf(employee.getId()).length()) + " |");

                        System.out.print( " " + employee.getPosition() + " ".repeat(biggestPosition >= postion.length() ?biggestPosition - postion.length() : 4 - biggestPosition) + " | ");

                        System.out.print(employee.getSalary() + " ".repeat(9 - String.valueOf(employee.getSalary()).length()) + " |\n");

                        System.out.println("+-----" + "-".repeat(biggestName - 4) + "-".repeat(text.length() + 12) + "-".repeat(biggestPosition) + "-+");


                    }
                    if(store.getEmployees().list_all().isEmpty()) {
                        break;
                    }

                    System.out.println("\nInsira o ID do funcionário:");
                    System.out.println("(0. Voltar)");
                    id = num_input(Messages.invalid_input(), 0, 0);

                    if(id == 0) {
                        limparTerminal();
                        break;
                    }

                    if(store.getEmployees().get_by_id(id) == null) {
                        table("Funcionário inexistente.");
                        wait(2);
                        limparTerminal();
                        break;
                    }

                    try {
                        store.getEmployees().remove(id);
                        table("Funcionário devidamente demitido.");
                        wait(2);
                        limparTerminal();
                        break;
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;

                case 8:

                    System.out.println("""
                            +--------------------------------+
                            |    Opções                      |
                            +--------------------------------+
                            |    1. Versão Simplificada      |
                            |    2. Versão Completa          |""");

                        System.out.println("""
                            +--------------------------------+
                            |    0. Voltar                   |
                            +--------------------------------+""");

                    choice = num_input(Messages.invalid_input(), 0, 2);
                    if(choice == 0) break;

                    try {
                        String report = store.get_sales_report(choice);
                        if(choice == 1) table(report);
                        else System.out.println(report);
                    } catch (Exception e) {
                        System.out.println(Messages.inexpected_error());
                    }

                    break;
                
            }
        }
    }

    public static ShoppingMall create_shopping_database() {
        ShoppingMall mall = new ShoppingMall("Cariri Shopping", "Avenida Castelo Branco", 4, 4);
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

        mall.getUsers().add(new User(1, "Alice Silva", "alice.silva@example.com", "pass1234", "Costumer"));
        mall.getUsers().add(new User(2, "Bruno Costa", "bruno.costa@example.com", "brunoPass21", "Costumer"));
        mall.getUsers().add(new User(3, "Carla Mendes", "carla.mendes@example.com", "carlaSecure45", "Costumer"));
        mall.getUsers().add(new User(4, "Diego Santos", "diego.santos@example.com", "santosPass2024", "Costumer"));
        mall.getUsers().add(new User(5, "Matheus Bezerra", "matheus.bezerra@aluno.ufca.edu.br", "pooUFCA", "Manager"));
        mall.getUsers().add(new User(6, "Guilherme Viana", "guilherme.batista@aluno.ufca.edu.br", "pooUFCA", "Manager"));

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
        limparTerminal();
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
                if(val > 9999999.9) throw new Exception("Valor excede o limite válido.");
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

        table("Criação de usuário");

        System.out.println("\nInsira seu nome:");
        String name = str_input("Formato de nome inválido, insira novamente.", true, 3);
        
        System.out.println("\nInsira seu email:");
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

        System.out.println("\nInsira sua senha:");
        String password = str_input("Formato de senha inválido, insira novamente.", true, 4);

        User user = null;
        try {
            int totalUsers = mall.getUsers().get_total_items();
            user = new User(totalUsers + 1, name, email, password, "User");
            mall.getUsers().add(user);
            table("Seja Bem-Vindo(a) " + user.getName() + "!");
            wait(2);
            limparTerminal();
        } catch (Exception e) {
            System.out.println(Messages.inexpected_error());
        }

        return user;
    }

    private static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void table(String text) {
        int tam = text.length();
        System.out.printf("+" + "-".repeat(tam+2) + "+\n");
        System.out.printf("| %s |\n", text);
        System.out.printf("+" + "-".repeat(tam+2) + "+\n");
    }

    private static void wait(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (Exception e) {}
    }
}


