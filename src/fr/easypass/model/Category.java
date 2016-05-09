package fr.easypass.model;

import java.util.HashSet;

public class Category {
	
	private String nom;
	private String logo;
	private HashSet<Password> passwords;
	
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
	
	public void addPassword(Password password){
		this.passwords.add(password);
		password.setCategory(this);
	}
	
	public void removePassword(Password password){
		this.passwords.remove(password);
		password.setCategory(null);
	}
	
	public HashSet<Password> getPasswords(){
		return this.passwords;
	}
	
	

}
