package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.easypass.validation.FormValidator;

public class Group extends FormValidator<Group>{

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
    
    public Group(String name, String description, String logo) {
    	this.name = name;
    	this.description = description;
    	this.logo = logo;
    }
    
    public Group getObj() {
        return this;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public Integer getId() {
    	return this.id;
    }

    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min=3, max=60,  message = "Le nom doit faire entre {min} et {max} charactères de long")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "La description ne peut pas être vide")
    @Size(min=3, max=1600,  message = "La description doit faire entre {min} et {max} charactères de long")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @NotNull(message = "Le logo ne peut pas être vide")
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
