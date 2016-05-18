package fr.easypass.db;

import java.util.HashMap;

import fr.easypass.model.Group;
import fr.easypass.model.User;

public class UserManager {

    private static String[][] getDatas() {

        String[][] datas = { 
                //1
                { "Adrien", "Turcey", "adrienturcey@outlook.com", "aturcey", "admin1234", "Blog moto,Bricolage"},
                //2
                { "Jonathan", "Cholet", "jonathancholet@gmail.com", "jcholet", "admin1234", "Blog moto,Bricolage"},
                //3
                { "Jeff", "Jacquelot", "jacquelotjeff@gmail.com", "jjacquelot", "admin1234", "Blog moto,Bricolage"},
                //4
                { "Yoann", "Keoxay", "keoxayyoann@gmail.com", "ykeoxay", "admin1234", "Blog moto,Bricolage"},
                //5
                { "Albert", "NGuyen", "albertnguyen@gmail.com", "anguyen", "admin1234", "Blog moto,Bricolage"},
                
                //6
                { "David", "Banta", "DavidJBanta@dayrep.com ", "Hadown42", "shaime5V", "Blog moto"},
                //7
                { "Debbie", "Taylor", "DebbieHTaylor@jourrapide.com", "Afterviout65", "sohvie9aKe", "Blog moto"},
                //8
                { "Robert", "Therrien", "RobertETherrien@teleworm.us ", "Duritat", "Fus6La3fie", "Blog moto"},
                //9
                { "Anthony", "Stewart", "AnthonyCStewart@armyspy.com", "Boxechan", "uo1Eu1Caga", "Blog moto"},
                //10
                { "Jasmine", "Thetford", "JasmineJThetford@rhyta.com", "Exagagaidid", "ohmoh3Si8ph", "Blog moto"},
                //11
                { "admin", "admin", "admin@easypass.com", "administrator", "admin1234" },
                
        };

        return datas;
    }

    /**
     * Return list of Users
     * 
     * @return
     */
    public HashMap<String, User> getUsers() {
        
        GroupManager groupManager = new GroupManager();
        HashMap<String, User> users = new HashMap<>();
        Integer cnt = 1;

        for (String[] userInfos : getDatas()) {
            User user = new User();
            user.setId(cnt);
            user.setFirstname(userInfos[0]);
            user.setLastname(userInfos[1]);
            user.setEmail(userInfos[2]);
            user.setUsername(userInfos[3]);
            user.setPassword(userInfos[3]);
            
            String[] groupNames = userInfos[4].split(",");
            
            //TODO Réussir  à ajouter les de la perosnne sans que ça plante.
            /*
            for (String groupName : groupNames) {
                Group group = groupManager.getGroup(groupName);
                user.addGroup(group);
            }
            */

            users.put(user.getUsername(), user);
        }

        return users;

    }

    /**
     * Check if user exists into database
     * 
     * @param username
     * @return
     */
    public Boolean checkLogin(String username) {
        HashMap<String, User> users = this.getUsers();
        return users.containsKey(username);
    }

    /**
     * Check if User and Password corresponds
     * 
     * @param username
     * @param password
     * @return
     */
    public Boolean checkLoginWithPassword(String username, String password) {
        HashMap<String, User> users = this.getUsers();
        User user = users.get(username);
        return (user.getPassword().equals(password));
    }

    /**
     * Return User object if existing into data
     * 
     * @param username
     * @return
     */
    public User getUser(String username) {
        return this.getUsers().get(username);
    }

}
