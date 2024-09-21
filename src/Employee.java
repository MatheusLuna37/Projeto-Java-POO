public class Employee {
    private int id;
    private String name;
    private String position;
    private float salary;

    public void promote(String new_position) {
        this.position = new_position;
    }

    public void update_salary(float new_salary) {
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

    public float getSalary() {
        return salary;
    }
}
