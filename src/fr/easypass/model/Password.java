package fr.easypass.model;

public class Password {
	
	private Integer id;
	private String name;
	private String siteUrl;
	private String password;
	private String informations;
	private Integer category;
	private Integer ownerUser;
	private Integer ownerGroup;
	
	public Integer getId(){
		return this.id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public void setOwnerUser(Integer userId){
		this.ownerUser = userId;
	}
	
	public Integer getOwnerUser(){
		return this.ownerUser;
	}
	
	public void setOwnerGroup(Integer groupId){
		this.ownerGroup = groupId;
	}
	
	public Integer getOwnerGroup(){
		return this.ownerGroup;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer categoryId) {
		this.category = categoryId;
	}	
	
}
