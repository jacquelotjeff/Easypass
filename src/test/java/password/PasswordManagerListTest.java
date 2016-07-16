package test.java.password;

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
public class PasswordManagerListTest extends TestCase {
    
    private static PasswordManager passwordManager;
    private static Map<Integer, Password> passwordsDb;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        passwordManager = new PasswordManager();
        passwordsDb = passwordManager.getPasswords();
    }
    
    @Parameters(name = "Get password {0}")
    public static Object data() {
        
        return new Object[][] {
            {1, 7, "Accès club de tennis", "", "Tennis club Moussy", "0Ipa7/jpJ6xlDHxcC9GnLQ==", "tennis"},
            {2, 7, "Code d'accès au gymnase", "", "Aucun", "JDiaeMPVCdLOYTOa23p71w==", "1354968"},
            {3, 7, "Mot de passe Membre VIP", "", "www.zone-tennis.com", "d84WZpBmkUvQZDSzJQb1euZRlUEDDjrp", "viptennis"},
            {4, 3, "Useless", "A supprimer !", "www.useless.fr", "URIOOHEqs/QpnXKubLpfqg==", "useless"},
            {5, 2, "Facebook", "", "www.facebook.com", "MGiwWxw4Fbd3+rplO5ohwg==", "fbjeff"},
            {6, 5, "VIP Imagine Dragons", "", "www.imagine-dragons.fr", "1A2o2KtPuAGqZYSBXZ6mdhpVHslqMHEf", "imaginedragons"},
        };
        
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
    public String encryptedPassword;
    
    @Parameter(6)
    public String plainPassword;
    
    @Test
    public void testListPassword() {
        
        Password passwordDb = passwordsDb.get(id);
        
        log.log(Level.INFO, "We're testing list password...");
        
        assertNotNull(passwordDb);
        
        assertEquals(id, passwordDb.getId());
        assertEquals(categoryId, passwordDb.getCategory());
        assertEquals(title, passwordDb.getTitle());
        assertEquals(informations, passwordDb.getInformations());
        assertEquals(urlSite, passwordDb.getSiteUrl());
        assertEquals(plainPassword, passwordDb.getPassword());
        
       
    }
}

