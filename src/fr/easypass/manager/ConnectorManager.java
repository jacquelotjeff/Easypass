package fr.easypass.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConnectorManager {
    
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());

    public static JsonObject getParameters() throws IOException {

        InputStream stream = ConnectorManager.class.getClassLoader().getResourceAsStream("config.properties");
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();

        return jsonObject;
    }

    public static Connection getConnection() throws IOException {

        JsonObject jsonObject = getParameters();
        String connector = jsonObject.get("database").getAsJsonObject().get("connector").getAsString();
        String user = jsonObject.get("database").getAsJsonObject().get("user").getAsString();
        String password = jsonObject.get("database").getAsJsonObject().get("password").getAsString();

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "Error while requesting JDBC Driver", e);
        }

        try {

            Properties properties = new Properties();

            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("connector", connector);
            
            log.log(Level.FINE, "@@@" + properties.toString());
            log.log(Level.FINE, "@@@" + connector.toString());

            conn = DriverManager.getConnection(connector, properties);

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error while building driver connection", e);
        }

        return conn;
    }

}
