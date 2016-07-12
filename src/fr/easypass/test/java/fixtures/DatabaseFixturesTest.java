package fr.easypass.test.java.fixtures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.easypass.manager.ConnectorManager;
import fr.easypass.utils.ScriptRunner;

public class DatabaseFixturesTest {
    
    public static final Logger log = Logger.getLogger(DatabaseFixturesTest.class.getName());

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testDatabaseFixtures() {
        
        Connection conn = null;
        
        try {
            conn = ConnectorManager.getConnection();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error when getting connection", e);
        }

        ScriptRunner runner = new ScriptRunner(conn, false, false);
        String file = DatabaseFixturesTest.class.getClassLoader().getResource("easypass-fixtures.sql").getPath();
        
        try {
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (IOException | SQLException e) {
            log.log(Level.SEVERE, "Error while running SQL Fixtures script.", e);
        }
        
    }

}
