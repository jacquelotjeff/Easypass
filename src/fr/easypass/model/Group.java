package fr.easypass.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Group {

    private String name;
    private String description;
    private String logo;
    private HashSet<User> users;
    private List<String> usersNames;
    private HashSet<User> administrators;
    private List<String> administratorsNames;
    private HashSet<Password> passwords;
    private List<String> passwordsNames;
    private HashSet<Category> categories;
    private List<String> categoriesNames;

    public Group() {
        this.users = new HashSet<User>();
        this.passwords = new HashSet<Password>();
        this.administrators = new HashSet<User>();
        this.categories = new HashSet<Category>();
        
        this.usersNames = new ArrayList<String>();
        this.administratorsNames = new ArrayList<String>();
        this.passwordsNames = new ArrayList<String>();
        this.categoriesNames = new ArrayList<String>();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public HashSet<User> getUsers() {
        return this.users;
    }

    public void addAdministrator(User user) {
        this.administrators.add(user);
    }

    public void removeAdministrator(User user) {
        this.administrators.remove(user);
    }

    public HashSet<User> getAdministrators() {
        return this.administrators;
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
    public void addUserName(String userName) {
        this.usersNames.add(userName);
    }
    
    public List<String> getUsersNames(){
        return this.usersNames;
    }
    
    public void addAdministratorName(String userName) {
        this.administratorsNames.add(userName);
    }
    
    public List<String> getAdministratorsNames(String administratorName) {
        return this.administratorsNames;
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
