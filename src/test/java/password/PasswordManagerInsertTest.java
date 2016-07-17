package test.java.password;

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
public class PasswordManagerInsertTest extends TestCase {
    
    private static PasswordManager passwordManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Insert password {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                //Insertion de deux mots de passes
                {7, 5,      "Bureau Windows", "Mot de passe du bureau.", "Aucun site", "mdpwindows98", "fmq9YEsMpWjvv1Xq/bMqws/8gFRgY4V2", 1, Password.OWNER_TYPE_USER, 0},
                {8, 3,      "Paypal", "Compte paypal boîte", "www.paypal.com", "mdppaypal", "0boM5ouM6+8omS9foqbC8RpgD0OTlmNZ", 1, Password.OWNER_TYPE_GROUP, 0},
                //Validation des champs.
                {9,  null,  "Mot de passe Membre VIP", "",              "www.zone-tennis.com",    "mdptest", "", 1,    Password.OWNER_TYPE_GROUP, 1},
                {10, 3,     "",                        "A supprimer !", "www.useless.fr",         "mdptest", "", 1,    Password.OWNER_TYPE_GROUP, 1},
                {11, 2,     "Facebook",                "",              "",                       "mdptest", "", 1,    Password.OWNER_TYPE_GROUP, 1},
                {12, 5,     "VIP Imagine Dragons",     "",              "www.imagine-dragons.fr", "",        "", 1,    Password.OWNER_TYPE_GROUP, 1},
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
    public String plainPassword;
    
    @Parameter(6)
    public String encryptedPassword;
    
    @Parameter(7)
    public Integer ownerId;
    
    @Parameter(8)
    public String ownerType;
    
    @Parameter(9)
    public int countErrors;
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException {
        passwordManager = new PasswordManager();
    }
    
    @Test
    public void testInsertPassword(){
        
        Password  password = new Password();
        
        password.setCategory(categoryId);
        password.setTitle(title);
        password.setInformations(informations);
        password.setSiteUrl(urlSite);
        password.setPassword(plainPassword);
        
        if (ownerType.equals(Password.OWNER_TYPE_USER)) {
            password.setOwnerUser(ownerId);
        } else {
            password.setOwnerGroup(ownerId);
        }
        
        log.log(Level.INFO, "We're checking the password validation...");
        
        assertEquals(countErrors, password.isValid().size());
        
        //If password is valid check the insert in database.
        if (countErrors == 0) {
            Integer success = passwordManager.insertPassword(title, urlSite, plainPassword, categoryId, informations, ownerId, ownerType);
            log.log(Level.INFO, "We're checking to insert password in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                
                Map<Integer, Password> passwords = passwordManager.getPasswords();
                
                Password passwordDb = passwords.get(id);
                
                assertNotNull(passwordDb);
                
                log.log(Level.INFO, "We're checking the password is correctly inserted...");
                
                assertEquals(passwordDb.getCategory(), categoryId);
                assertEquals(passwordDb.getTitle(), title);
                assertEquals(passwordDb.getInformations(), informations);
                assertEquals(passwordDb.getSiteUrl(), urlSite);
                assertEquals(passwordDb.getPassword(), plainPassword);
                
                Map<Integer, Password> passwordsOwner;
                
                if (ownerType.equals(Password.OWNER_TYPE_GROUP)) {
                    passwordsOwner = passwordManager.getPasswordsByGroup(ownerId);
                } else {
                    passwordsOwner = passwordManager.getPasswordsByUser(ownerId);
                }
                
                Boolean hasPassword = passwordsOwner.containsKey(id);
                assertTrue(hasPassword);

                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get passwords", e);
            }
            
        }
    }
}
