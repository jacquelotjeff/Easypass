package test.java.password;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
public class PasswordManagerDeleteTest extends TestCase {
    
    private static PasswordManager passwordManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Delete password {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {4, 3, "Useless", "A supprimer !", "www.useless.fr", "useless"},
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
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException {
        passwordManager = new PasswordManager();
    }
    
    @Test
    public void testDeletePassword(){
        try {
            
            log.log(Level.INFO, "We're testing a password deletion...");
            
            Password passwordDb;
            
            passwordDb = passwordManager.getPassword(id);
            assertEquals(categoryId, passwordDb.getCategory());
            assertEquals(title, passwordDb.getTitle());
            assertEquals(informations, passwordDb.getInformations());
            assertEquals(urlSite, passwordDb.getSiteUrl());
            assertEquals(plainPassword, passwordDb.getPassword());
            
            Integer success = passwordManager.deletePassword(id);
            assertEquals(success, (Integer) 1);
            
            passwordDb = passwordManager.getPassword(id);
            
            assertNull(passwordDb);
                    
            
        } catch (IOException e) {
            log.log(Level.SEVERE, "Impossible to get password from PasswordManager", e);
        }
    }
}
