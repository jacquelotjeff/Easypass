package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import fr.easypass.validation.formValidator;

public class User extends formValidator<User> {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private List<Integer> groups;
    private List<Integer> passwords;
    private List<Integer> categories;

    // Constructor
    public User() {
        this.groups = new ArrayList<Integer>();
        this.passwords = new ArrayList<Integer>();
        this.categories = new ArrayList<Integer>();
    }
    
    public User(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public User getObj() {
        return this;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = "${validatedValue} ne peut pas être vide")
    @Size(min=3, max=16,  message = "${validatedValue} doit faire entre {min} et {max} charactères de long")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fisrtname) {
        this.firstname = fisrtname;
    }

    @NotNull(message = "${validatedValue} ne peut pas être vide")
    @Size(min=3, max=16, message = "${validatedValue} doit faire entre {min} et {max} charactères")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @NotNull(message = "${validatedValue} ne peut pas être vide")
    @Size(min=3, max=16, message = "${validatedValue} doit faire entre {min} et {max} charactères")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @NotNull(message = "${validatedValue} ne peut pas être vide")
    @Size(min=3, max=16, message = "${validatedValue} doit faire entre {min} et {max} charactères")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "${validatedValue} ne peut pas être vide")
    @Pattern(regexp = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", message = "${validatedValue} n'est pas un mail valide")  
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addGroup(Integer groupId) {
        this.groups.add(groupId);
    }

    public List<Integer> getGroups() {
        return this.groups;
    }
    
    public void addCategory(Integer categoryId) {
        this.categories.add(categoryId);
    }

    public List<Integer> getCategories() {
        return this.categories;
    }

    public void addPassword(Integer passwordId) {
        this.passwords.add(passwordId);
    }

    public List<Integer> getPasswords() {
        return this.passwords;
    }
}
