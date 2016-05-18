package fr.easypass.model;

import java.util.HashSet;

public class Group {

    private String name;
    private String description;
    private String logo;
    private HashSet<User> users;
    private HashSet<User> administrators;
    private HashSet<Password> passwords;
    private HashSet<Category> categories;

    public Group() {
        this.users = new HashSet<User>();
        this.passwords = new HashSet<Password>();
        this.administrators = new HashSet<User>();
        this.categories = new HashSet<Category>();
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

}
