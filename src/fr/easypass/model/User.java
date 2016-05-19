package fr.easypass.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private HashSet<Group> groups;
    private List<String> groupsNames;
    private HashSet<Password> passwords;
    private List<String> passwordsNames;
    private HashSet<Category> categories;
    private List<String> categoriesNames;

    // Constructor
    public User() {
        this.groups = new HashSet<Group>();
        this.passwords = new HashSet<Password>();
        this.categories = new HashSet<Category>();
        
        this.groupsNames = new ArrayList<String>();
        this.passwordsNames = new ArrayList<String>();
        this.categoriesNames = new ArrayList<String>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public HashSet<Group> getGroups() {
        return this.groups;
    }
    
    public void addCategory(Category category) {
        this.categories.add(category);
        category.setOwner(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public HashSet<Category> getCategories() {
        return this.categories;
    }

    public void addPassword(Password password) {
        this.passwords.add(password);
        password.setOwner(this);
    }

    public void removePassword(Password password) {
        this.passwords.remove(password);
        password.setOwner(null);
    }

    public HashSet<Password> getPasswords() {
        return this.passwords;
    }
    
    
    //TODO Retirer lorsque qu'une base de données sera mises en place
    /* Provisoire permet de stocker les listes sans faire planter */
    public void addGroupName(String group) {
        this.groupsNames.add(group);
    }
    
    public List<String> getGroupsNames(){
        return this.groupsNames;
    }
    
    public void addPasswordName(String password) {
        this.passwordsNames.add(password);
    }
    
    public List<String> getPasswordsNames(){
        return this.passwordsNames;
    }
    
    public void addCategoryName(String category) {
        this.categoriesNames.add(category);
    }
    
    public List<String> getCategoriesNames(){
        return this.categoriesNames;
    }
}
