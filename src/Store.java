import java.util.ArrayList;

public class Store {
    private int id;
    private String name;
    private String category;
    private int floor_located;
    private EmployeeRepository employees;
    private ProductRepository products;
    private SalesReport report;

    public boolean hire_employee(Employee employee) {
        if (this.employees.add(employee)) {
            return true;
        }
        return false;
    }

    public boolean fire_employee(int employee_id) {
        if (this.employees.remove(employee_id)) {
            return true;
        }
        return false;
    }

    public ArrayList<Employee> find_employees_by_position(String position) {
        return this.employees.find_by_position(position);
    }

    public void show_all_employees() {
        for (Employee employee : this.employees.list_all()) {
            System.out.println(employee.getName() + employee.getPosition());
        }
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
