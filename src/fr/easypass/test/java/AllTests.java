package fr.easypass.test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.easypass.test.java.category.CategoryManagerDeleteTest;
import fr.easypass.test.java.category.CategoryManagerEditTest;
import fr.easypass.test.java.category.CategoryManagerInsertTest;
import fr.easypass.test.java.category.CategoryManagerListTest;
import fr.easypass.test.java.fixtures.DatabaseFixturesTest;
import fr.easypass.test.java.group.GroupManagerAddUserTest;
import fr.easypass.test.java.group.GroupManagerDeleteTest;
import fr.easypass.test.java.group.GroupManagerDeleteUserTest;
import fr.easypass.test.java.group.GroupManagerEditTest;
import fr.easypass.test.java.group.GroupManagerInsertTest;
import fr.easypass.test.java.group.GroupManagerListTest;
import fr.easypass.test.java.group.GroupManagerUserAdminTest;
import fr.easypass.test.java.password.PasswordManagerDeleteTest;
import fr.easypass.test.java.password.PasswordManagerEditTest;
import fr.easypass.test.java.password.PasswordManagerInsertTest;
import fr.easypass.test.java.password.PasswordManagerListTest;
import fr.easypass.test.java.user.UserManagerDeleteTest;
import fr.easypass.test.java.user.UserManagerEditTest;
import fr.easypass.test.java.user.UserManagerInsertTest;
import fr.easypass.test.java.user.UserManagerListTest;

@RunWith(Suite.class)
@SuiteClasses({
    //Fixtures
    DatabaseFixturesTest.class,
    //User manager tests
    UserManagerListTest.class,
    UserManagerEditTest.class,
    UserManagerInsertTest.class,
    UserManagerDeleteTest.class,
    
    //Category manager tests
    CategoryManagerListTest.class,
    CategoryManagerEditTest.class,
    CategoryManagerInsertTest.class,
    CategoryManagerDeleteTest.class,
    
    //Group manager tests
    GroupManagerListTest.class,
    GroupManagerEditTest.class,
    GroupManagerInsertTest.class,
    GroupManagerDeleteTest.class,
    GroupManagerAddUserTest.class,
    GroupManagerDeleteUserTest.class,
    GroupManagerUserAdminTest.class,
    
    //Password manager tests
    PasswordManagerListTest.class,
    PasswordManagerEditTest.class,
    PasswordManagerInsertTest.class,
    PasswordManagerDeleteTest.class,
    
})
public class AllTests {
    
}
