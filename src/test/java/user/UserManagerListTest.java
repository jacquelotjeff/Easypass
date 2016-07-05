package test.java.user;

import java.util.Map;
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
public class UserManagerListTest extends TestCase {
    
    private static UserManager userManager;
    private static Map<Integer, User> usersDb;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        userManager = new UserManager();
        usersDb = userManager.getUsers();
    }
    
    @Parameters(name = "Getting users")
    public static Object data() {
        
        return new Object[][] {
            {40, "aturcey", "Adrien", "Turcey", "7b902e6ff1db9f560443f2048974fd7d386975b0", "adrienturcey@gmail.com", true},
            {41, "jcholet", "Jonathan", "Cholet", "7b902e6ff1db9f560443f2048974fd7d386975b0", "jcholet@gmail.com", false},
            {42, "jjacquelot", "Jeff", "Jacquelot", "7b902e6ff1db9f560443f2048974fd7d386975b0", "jjacquelot@gmail.com", false},
            {43, "useless", "Use", "Less", "7b902e6ff1db9f560443f2048974fd7d386975b0", "useless@gmail.com", false},
        };
        
    }
    
    @Parameter(0)
    public Integer id;

    @Parameter(1)
    public String username;
    
    @Parameter(2)
    public String firstname;
    
    @Parameter(3)
    public String lastname;
    
    @Parameter(4)
    public String password;
    
    @Parameter(5)
    public String email;
    
    @Parameter(6)
    public Boolean admin;
    
    @Test
    public void testListUser() {
                
        User userDb = usersDb.get(id);
        
        //assertEquals(usersDb.size(), expectedUsers.length);
        
        assertNotNull(userDb);
        
        assertEquals(id, userDb.getId());
        assertEquals(username, userDb.getUsername());
        assertEquals(firstname, userDb.getFirstname());
        assertEquals(lastname, userDb.getLastname());
        assertEquals(password, userDb.getPassword());
        assertEquals(email, userDb.getEmail());
        assertEquals(admin, userDb.getAdmin());
       
    }
}

