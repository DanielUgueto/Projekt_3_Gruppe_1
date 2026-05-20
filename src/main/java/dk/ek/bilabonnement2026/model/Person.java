package dk.ek.bilabonnement2026.model;

/*
Fælles superklasse for personer i systemet (Customer og Employee).

Klassen er abstract så en person i sig selv ikke kan instansieres, det skal være
Customer eller Employee

Protected felter så sub-klasserne kan læse dem.
 */
public abstract class Person {
    protected  String firstName;
    protected String lastName;
    protected String email;
    protected boolean isActive;

    public Person(String firstName, String lastName, String email, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
