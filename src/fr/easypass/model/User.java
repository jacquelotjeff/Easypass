package fr.easypass.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import fr.easypass.validation.FormValidator;

public class User extends FormValidator<User> {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private Boolean admin;
    
    private List<Integer> groups;
    private List<Integer> passwords;
    private List<Integer> categories;

    // Constructor
    public User() {
        this.groups = new ArrayList<Integer>();
        this.passwords = new ArrayList<Integer>();
        this.categories = new ArrayList<Integer>();
    }
    
    public User(String firstname, String lastname, String username, String password, String email, Boolean admin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.admin = admin;
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


    @NotNull(message = "Le prénom ne peut pas être vide")
    @Size(min=3, max=16,  message = "Le prénom doit faire entre {min} et {max} charactères de long")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fisrtname) {
        this.firstname = fisrtname;
    }

    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min=3, max=16, message = "Le nom doit faire entre {min} et {max} charactères")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @NotNull(message = "Le nom d'utilisateur ne peut pas être vide")
    @Size(min=3, max=16, message = "Le nom d'utilisateur doit faire entre {min} et {max} charactères")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @NotNull(message = "Le mot de passe ne peut pas être vide")
    @Size(min=3, max=16, message = "Le mot de passe doit faire entre {min} et {max} charactères")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "L'email ne peut pas être vide")
    @Pattern(regexp = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", message = "Le champ mail ne contient pas un mail valide")  
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    public HashMap<String, String> isValidWithoutPassword() {

        HashMap<String, String> errors = new HashMap<>();
        // do your custom validations if needed
        Set<ConstraintViolation<User>> validation = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(getObj());
        if (validation.size() > 0) {
            for (ConstraintViolation<User> error : validation) {
            	String propertyPath = error.getPropertyPath().toString();
            	if (!propertyPath.equals("password")) {
            		errors.put(error.getPropertyPath().toString(), error.getMessage());
            	}
            }
        }
        return errors;
    }

}
