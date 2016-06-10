package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
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
    
    public User getObj() {
        return this;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Size(min=3, max=16)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fisrtname) {
        this.firstname = fisrtname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
