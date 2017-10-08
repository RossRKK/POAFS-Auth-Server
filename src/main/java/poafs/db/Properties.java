package poafs.db;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Properties {
	public static void setup() {

        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static Map<String, String> getPropertiesForTableAutoCreation() {

        return getProperties("create");
    }

    public static Map<String, String> getPropertiesForTableValidation() {

        return getProperties("validate");
    }

    /*
     * The properties Map contains all required parameters to build an EntityManager
     * See EntityManager being created in E.g.Store.java
     * Read this information from database.properties and populate it
     */
    private static Map<String, String> getProperties(String hibernate_option) {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", hibernate_option);

        // the driver - this needs to be part of the project's classpath
        // see: https://studres.cs.st-andrews.ac.uk/CS1003/Examples/W06-1-JDBC/READ-ME.txt
        properties.put("javax.persistence.jdbc.driver", "org.mariadb.jdbc.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        
        // Login details
        properties.put("javax.persistence.jdbc.url", "jdbc:mariadb:localhost/poafs");
        properties.put("javax.persistence.jdbc.user", "root");
        properties.put("javax.persistence.jdbc.password", "DoctorWh0!");
        
        return properties;
    }
}
