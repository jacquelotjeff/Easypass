package fr.easypass.test.java.password;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.PasswordManager;
import fr.easypass.model.Password;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class PasswordManagerEditTest extends TestCase {
    
    private static PasswordManager passwordManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Edit password {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {5, 4, "Gmail", "", "www.gmail.com", "mdptest"},
            }
        );
    }
    
    @Parameter(0)
    public Integer id;

    @Parameter(1)
    public Integer categoryId;
    
    @Parameter(2)
    public String title;
    
    @Parameter(3)
    public String informations;
    
    @Parameter(4)
    public String urlSite;
    
    @Parameter(5)
    public String pass;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        passwordManager = new PasswordManager();
    }
    
    @Test
    public void testEditPassword(){
        
        Password password;
        
        try {
            
            password = passwordManager.getPassword(id);
            password.setCategory(categoryId);
            password.setTitle(title);
            password.setInformations(informations);
            password.setSiteUrl(urlSite);
            password.setPassword(pass);
            
            assertEquals(0, password.isValid().size());
            
        } catch (IOException e2) {
            log.log(Level.SEVERE, "Impossible to get password from password manager", e2);
        } 
        
        //If password is valid check the edit in database.
        Integer success;
        try {
            
            success = passwordManager.editPassword(id, title, urlSite, pass, categoryId, informations);
            log.log(Level.INFO, "We're checking to edit password in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                Map<Integer, Password> passwords = passwordManager.getPasswords();
                
                Password passwordDb = passwords.get(id);
                
                log.log(Level.INFO, "We're checking the password is correctly edited...");
                assertEquals(passwordDb.getCategory(), categoryId);
                assertEquals(passwordDb.getTitle(), title);
                assertEquals(passwordDb.getInformations(), informations);
                assertEquals(passwordDb.getSiteUrl(), urlSite);
                assertEquals(passwordDb.getPassword(), pass);

                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get passwords", e);
            }
            
        } catch (IOException e1) {
            log.log(Level.SEVERE, "Impossible to edit a password from PasswordManager (editPassword)", e1);
        }
    }
}
