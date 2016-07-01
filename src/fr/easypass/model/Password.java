package fr.easypass.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fr.easypass.validation.FormValidator;

public class Password extends FormValidator<Password> {

    private Integer id;
    private String title;
    private String siteUrl;
    private String password;
    private String informations;
    private Integer category;
    private Integer ownerUser;
    private Integer ownerGroup;
    private String typeOwner;

    public static final String OWNER_TYPE_GROUP = "G";
    public static final String OWNER_TYPE_USER = "U";

    public Password() {
    }

    public Password(String title, String siteUrl, String password, String informations, Integer category) {
        this.title = title;
        this.siteUrl = siteUrl;
        this.password = password;
        this.informations = informations;
        this.category = category;
    }

    public Password getObj() {
        return this;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = "Le titre ne peut pas être vide")
    @Size(min = 3, max = 60, message = "Le titre du mot de passe doit faire entre {min} et {max} charactères de long")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "Le site ne peut pas être vide")
    @Size(min = 3, max = 255, message = "Le site doit faire entre {min} et {max} charactères de long")
    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @NotNull(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 3, max = 16, message = "Le mot de passe doit faire entre {min} et {max} charactères de long")
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

    public void setOwnerUser(Integer userId) {
        this.ownerUser = userId;
        this.typeOwner = Password.OWNER_TYPE_USER;
    }

    public Integer getOwnerUser() {
        return this.ownerUser;
    }

    public void setOwnerGroup(Integer groupId) {
        this.ownerGroup = groupId;
        this.typeOwner = Password.OWNER_TYPE_GROUP;
    }

    public Boolean ownerIsGroup() {
        return this.typeOwner.equals(Password.OWNER_TYPE_GROUP);
    }

    public Boolean ownerIsUser() {
        return this.typeOwner.equals(Password.OWNER_TYPE_USER);
    }

    public Integer getOwnerGroup() {
        return this.ownerGroup;
    }

    @NotNull(message = "Le mot de passe doit être associé à une catégorie.")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer categoryId) {
        this.category = categoryId;
    }

}
