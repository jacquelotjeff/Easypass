package fr.easypass.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Category {

    private String nom;
    private String logo;
    private Object owner;
    private HashSet<Password> passwords;
    private List<String> passwordsNames;

    public Category() {
        this.passwords = new HashSet<Password>();
        
        this.passwordsNames = new ArrayList<String>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Object getOwner() {
        return this.owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public void addPassword(Password password) {
        this.passwords.add(password);
        password.setCategory(this);
    }

    public void removePassword(Password password) {
        this.passwords.remove(password);
        password.setCategory(null);
    }

    public HashSet<Password> getPasswords() {
        return this.passwords;
    }
    
    //TODO a retirer lorsque la base sera mis en place
    public void addPasswordName(String password) {
        this.passwordsNames.add(password);
    }
    
    public List<String> getPasswordsNames(){
        return this.passwordsNames;
    }

}
