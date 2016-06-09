package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private Integer id;
    private String name;
    private String description;
    private String logo;
    private List<Integer> users;
    private List<Integer> administrators;
    private List<Integer> passwords;
    private List<Integer> categories;

    public Group() {
        this.users = new ArrayList<Integer>();
        this.passwords = new ArrayList<Integer>();
        this.administrators = new ArrayList<Integer>();
        this.categories = new ArrayList<Integer>();
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public Integer getId() {
    	return this.id;
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

    public void addUser(Integer userId) {
        this.users.add(userId);
    }

    public List<Integer> getUsers() {
        return this.users;
    }

    public void addAdministrator(Integer userId) {
        this.administrators.add(userId);
    }


    public List<Integer> getAdministrators() {
        return this.administrators;
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
