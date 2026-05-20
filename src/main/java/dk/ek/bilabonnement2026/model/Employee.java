package dk.ek.bilabonnement2026.model;

public class Employee extends Person {
    int employeeId;
    String password;
    String role;

    public Employee(int employeeId, String firstName, String lastName, String password, String email, String role, boolean isActive) {
        super(firstName, lastName, email, isActive);
        this.employeeId = employeeId;
        this.password = password;
        this.role = role;
    }

    public Employee(String firstName, String lastName, String password, String email, String role) {
        super(firstName, lastName, email, true);
        this.password = password;
        this.role = role;
    }

    public Employee(int employeeId, String firstName, String lastName, String email, String role, boolean isActive) {
        super(firstName, lastName, email, isActive);
        this.employeeId = employeeId;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}
