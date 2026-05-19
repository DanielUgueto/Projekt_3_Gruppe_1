package dk.ek.bilabonnement2026.model;

public class Employee {
    int employeeId;
    String firstName;
    String lastName;
    String password;
    String workEmail;
    String role;
    boolean isActive;

    public Employee(int employeeId, String firstName, String lastName, String password, String workEmail, String role, boolean isActive) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.workEmail = workEmail;
        this.role = role;
        this.isActive = isActive;
    }

    public Employee(String firstName, String lastName, String password, String workEmail, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.workEmail = workEmail;
        this.role = role;
    }

    public Employee(int employeeId, String firstName, String lastName, String workEmail, String role, boolean isActive){
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workEmail = workEmail;
        this.role = role;
        this.isActive = isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean getIsActive(){
        return isActive;
    }
}
