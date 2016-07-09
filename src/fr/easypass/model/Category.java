package fr.easypass.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.easypass.validation.FormValidator;

public class Category extends FormValidator<Category> {

    private Integer id;
    private String name;
    private String logo;
    private URL logoURL;
    
    private List<Integer> passwords;

    public Category() {
    	this.passwords = new ArrayList<Integer>();
    }
    
    public Category(String name, String logo) {
    	this.name = name;
    	this.logo = logo;
    }
    
    public Category getObj() {
        return this;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  	@NotNull(message = "Le nom ne peut pas être vide")
    @Size(min=3, max=60,  message = "Le nom doit faire entre {min} et {max} charactères de long")  
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
    
    public URL getLogoURL(){
        return this.logoURL;
    }
    
    public void setLogoURL(URL url){
        this.logoURL = url;
    }

    public void addPassword(Integer passwordId) {
        this.passwords.add(passwordId);
    }

    public List<Integer> getPasswords() {
        return this.passwords;
    }

}
