package fr.easypass.manager;

import java.util.HashMap;

import fr.easypass.manager.UserManager;
import fr.easypass.model.Password;
import fr.easypass.model.User;

public class PasswordManager {

    /**
     * Return list of Users
     * 
     * @return
     */
    public HashMap<String, Password> getPasswords() {

        HashMap<String, Password> passwords = new HashMap<>();
        Password password = new Password();
        UserManager userManager = new UserManager();

        User owner = userManager.getUser("aturcey");

        password.setNom("allocine");
        password.setSiteUrl("allocine.fr");
        password.setPassword("patatedeforain");
        password.setInformations("mot de passe pour allocine");
        password.setOwner(owner);

        passwords.put(password.getNom(), password);

        return passwords;

    }

    /**
     * Return User object if existing into data
     * 
     * @param username
     * @return
     */
    public Password getPassword(String password) {
        return this.getPasswords().get(password);
    }
}
