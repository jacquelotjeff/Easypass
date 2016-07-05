package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.fixtures.DatabaseFixturesTest;
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
    UserManagerDeleteTest.class
})
public class AllTests {
    
}
