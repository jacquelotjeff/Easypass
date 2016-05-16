package fr.easypass.model;

import java.util.HashSet;


public class User {
    
    private Integer id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private HashSet<Group> groups;
	private HashSet<Password> passwords;
	
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
	
	public void addGroup(Group group){
		this.groups.add(group);
	}
	
	public void removeGroup(Group group){
		this.groups.remove(group);
	}
	
	public HashSet<Group> getGroups(){
		return this.groups;
	}
	
	public void addPassword(Password password){
		this.passwords.add(password);
		password.setOwner(this);
	}
	
	public void removePassword(Password password){
		this.passwords.remove(password);
		password.setOwner(null);
	}
	
	public HashSet<Password> getPasswords(){
		return this.passwords;
	}
	
}
