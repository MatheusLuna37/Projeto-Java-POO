package business;
public class Employee {
    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee(int id, String name, String positiion, double salary) {
        this.id = id;
        this.name = name;
        this.position = positiion;
        this.salary = salary;
    }

    public void promote(String new_position) {
        this.position = new_position;
    }

    public void update_salary(double new_salary) {
        this.salary = new_salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }
}
