import java.util.ArrayList;

public class EmployeeRepository extends BaseRepository<Employee> {
    public ArrayList<Employee> find_by_position(String position) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee employee : this.data) {
            if (employee.getPosition().equals(position)) {
                result.add(employee);
            }
        }
        return result;
    }
}
