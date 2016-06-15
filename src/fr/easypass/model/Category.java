package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private Integer id;
    private String name;
    private String logo;
    
    private List<Integer> passwords;

    public Category() {
    	this.passwords = new ArrayList<Integer>();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void addPassword(Integer passwordId) {
        this.passwords.add(passwordId);
    }

    public List<Integer> getPasswords() {
        return this.passwords;
    }

}
