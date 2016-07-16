package test.java.user;

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
public class UserManagerInsertTest extends TestCase {
    
    private static UserManager userManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Insert user {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"anguyen", "Albert", "Nguyen", "admin1234", "anguyen@gmail.com", false, 0, 5},
                {"ykeoxay", "Yoann", "Keoxay", "admin1234", "ykeoxay@gmail.com", true, 0, 6},
                {"", "Jonathan", "Cholet", "admin1234", "jcholet@gmail.com", false, 1, 0},
                {"jcholet", "", "Cholet", "admin1234", "jcholet@gmail.com", false, 1, 0},
                {"jcholet", "Jonathan", "", "admin1234", "jcholet@gmail.com", false, 1, 0},
                {"jcholet", "Jonathan", "Cholet", "", "jcholet@gmail.com", false, 1, 0},
                {"jcholet", "Jonathan", "Cholet", "admin1234", "", false, 1, 0}
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
    public int countErrors;
    
    @Parameter(7)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        userManager = new UserManager();
    }
    
    @Test
    public void testInsertUser(){
        
        User  user = new User(); 
        
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setAdmin(admin);
        
        log.log(Level.INFO, "We're checking the user validation...");
        assertEquals(countErrors, user.isValid().size());
        
        //If user is valid check the insert in database.
        if (countErrors == 0) {
            Integer success = userManager.insertUser(user);
            log.log(Level.INFO, "We're checking to insert user in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                
                Map<Integer, User> users = userManager.getUsers();
                
                User userDb = users.get(id);
                
                assertNotNull(userDb);
                
                log.log(Level.INFO, "We're checking the user is correctly inserted...");
                assertEquals(username, userDb.getUsername());
                assertEquals(firstname, userDb.getFirstname());
                assertEquals(lastname, userDb.getLastname());
                //TODO Checking the SHA1 crypt
                //assertEquals(password, userDb.getPassword());
                assertEquals(email, userDb.getEmail());
                assertEquals(admin, userDb.getAdmin());

                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get users", e);
            }
            
        }
    }
}
