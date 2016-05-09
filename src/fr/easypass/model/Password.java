package fr.easypass.model;

public class Password {
	
	private String nom;
	private String siteUrl;
	private String password;
	private String informations;
	private Object owner;
	private Category category;
	private Integer ownerType;
	
	public final Integer OWNER_TYPE_GROUP = 0;
	public final Integer OWNER_TYPE_USER = 1;
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getSiteUrl() {
		return siteUrl;
	}
	
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getInformations() {
		return informations;
	}
	
	public void setInformations(String informations) {
		this.informations = informations;
	}
	
	public void setOwner(Object owner){
				
		if(owner instanceof Group){
			
			this.ownerType = this.OWNER_TYPE_GROUP;
			Group group = (Group) owner;
			
			//If the password not exists in the Group
			if(!group.getPasswords().contains(this)){
				group.addPassword(this);
			}
			
		} else {
			
			this.ownerType = this.OWNER_TYPE_USER;
			User user = (User) owner;
			
			//If the password not exists in the User
			if(!user.getPasswords().contains(this)){
				user.addPassword(this);
			}
			
		}
		
		this.owner = owner;
		
	}
	
	public Object getOwner(){
		return this.owner;
	}
	
	public Integer getOwnerType(){
		return this.ownerType;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		
		this.category = category;
		
		if(!category.getPasswords().contains(this)){
			category.addPassword(this);
		}
	}	
	
}
