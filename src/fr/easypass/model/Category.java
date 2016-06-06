package fr.easypass.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String nom;
    private String logo;
    private Object owner;
    private List<Integer> passwords;

    public Category() {
    	this.passwords = new ArrayList<Integer>();
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

    public void addPassword(Integer passwordId) {
        this.passwords.add(passwordId);
    }

    public List<Integer> getPasswords() {
        return this.passwords;
    }
}
