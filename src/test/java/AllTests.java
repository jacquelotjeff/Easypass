package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.category.CategoryManagerDeleteTest;
import test.java.category.CategoryManagerEditTest;
import test.java.category.CategoryManagerInsertTest;
import test.java.category.CategoryManagerListTest;
import test.java.fixtures.DatabaseFixturesTest;
import test.java.group.GroupManagerAddUserTest;
import test.java.group.GroupManagerDeleteTest;
import test.java.group.GroupManagerDeleteUserTest;
import test.java.group.GroupManagerEditTest;
import test.java.group.GroupManagerInsertTest;
import test.java.group.GroupManagerListTest;
import test.java.group.GroupManagerUserAdminTest;
import test.java.user.UserManagerDeleteTest;
import test.java.user.UserManagerEditTest;
import test.java.user.UserManagerInsertTest;
import test.java.user.UserManagerListTest;

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
    GroupManagerUserAdminTest.class
    
    
})
public class AllTests {
    
}
