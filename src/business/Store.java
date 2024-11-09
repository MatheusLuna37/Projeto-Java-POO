package business;
import persistence.EmployeeRepository;
import persistence.ProductRepository;

public class Store {
    private int id;
    private String name;
    private String category;
    private int floor_located;
    private EmployeeRepository employees;
    private ProductRepository products;
    private SalesReport report;

    public Store(int id, String name, String category, int floor_located) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.floor_located = floor_located;
        this.employees = new EmployeeRepository();
        this.products = new ProductRepository();
        this.report = new SalesReport();
    }

    public boolean hire_employee(Employee employee) {
        return this.employees.add(employee);
    }

    public boolean fire_employee(int employee_id) {
        return this.employees.remove(employee_id);
    }

    public void restock_product(Product product, int amount) {
        product.restock(amount);
        if (this.products.update(product)) {
        } else {
            this.products.add(product);
        }
    }

    public boolean sell_product(Product product, int quantity) {
        if (this.products.remove(product.getId(), quantity)) {
            this.report.register_sale(product, quantity);
            return true;
        }
        return false;
    }

    public String get_sales_report(int version) {
        switch (version) {
            case 1:
                return this.report.generate_simplified_version();
            case 2:
                return this.report.generate_complete_version();
            default:
                return "Invalid Version";
        }
    }

    public String getCategory() {
        return category;
    }

    public EmployeeRepository getEmployees() {
        return employees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductRepository getProducts() {
        return products;
    }

    public int getFloor_located() {
        return floor_located;
    }
}
