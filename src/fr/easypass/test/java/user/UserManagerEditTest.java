package fr.easypass.test.java.user;

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
import fr.easypass.manager.UserManager;
import fr.easypass.model.User;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class UserManagerEditTest extends TestCase {
    
    private static UserManager userManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Edit user {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"aturcey", "Adrien", "Turcey", "admin1234", "adrienturcey@outlook.com", true, 1}
            }
        );
    }
    
    @Parameter(0)
    public String username;

    @Parameter(1)
    public String firstname;
    
    @Parameter(2)
    public String lastname;
    
    @Parameter(3)
    public String password;
    
    @Parameter(4)
    public String email;
    
    @Parameter(5)
    public Boolean admin;
    
    @Parameter(6)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        userManager = new UserManager();
    }
    
    @Test
    public void testEditUser(){
        
        User  user = new User(); 
        
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setAdmin(admin);
        
        assertEquals(0, user.isValid().size());
        
        //If user is valid check the insert in database.
        Integer success;
        try {
            
            success = userManager.editUser(id, user);
            log.log(Level.INFO, "We're checking to edit user in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                Map<Integer, User> users = userManager.getUsers();
                
                User userDb = users.get(id);
                
                log.log(Level.INFO, "We're checking the user is correctly edited...");
                assertEquals(username, userDb.getUsername());
                assertEquals(firstname, userDb.getFirstname());
                assertEquals(lastname, userDb.getLastname());
                //TODO Checking the SHA1 crypt
                //assertEquals(password, userDb.getPassword());
                assertEquals(email, userDb.getEmail());
                log.log(Level.INFO, email);
                assertEquals(admin, userDb.getAdmin());

                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get users", e);
            }
            
        } catch (IOException e1) {
            log.log(Level.SEVERE, "Impossible to edit a user from UserManager (editUser)", e1);
        }
    }
}
