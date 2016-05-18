package fr.easypass.db;

import java.util.HashMap;

import fr.easypass.model.Group;

public class GroupManager {

    private static String[][] getDatas() {

        String[][] datas = { 
                { 
                    "Blog moto", 
                    "Groupe des personnes présente sur le blog Moto",
                    "http://icons.iconarchive.com/icons/icons8/android/512/Transport-Motorcycle-icon.png", 
                    "aturcey|jcholet|jjacquelot|ykeoxay|anguyen|Hadown42|Afterviout65|Duritat", 
                    "aturcey|jcholet|jjacquelot|ykeoxay|anguyen"
                    // TODO Mot de passes à associer
                    // TODO Catégories à associer
                },
                { 
                    "Bricolage", 
                    "Applications liées aux bricolage",
                    "http://freeflaticons.com/wp-content/uploads/2014/09/hammer-copy-1410948738nk4g8.png",
                    "aturcey|jcholet|jjacquelot|ykeoxay|anguyen",
                    "aturcey"
                    // TODO Mot de passes à associer
                    // TODO Catégories à associer
                },

        };

        return datas;
    }
    
    public HashMap<String, Group> getGroups() {
        
        UserManager userManager = new UserManager();
        
        HashMap<String, Group> groups = new HashMap<>();
         
        for (String[] groupInfos : getDatas()) {
            Group group = new Group();
            
            group.setName(groupInfos[0]);
            group.setDescription(groupInfos[1]);
            group.setLogo(groupInfos[2]);
               
            //Add group users
            String[] members = groupInfos[3].split("|");
            
            for (String username : members) {
                group.addUser(userManager.getUser(username));
            }
            
            //Add group administrators
            String[] administrators = groupInfos[4].split("|");
            
            for (String username : administrators) {
                group.addAdministrator(userManager.getUser(username));
            }
            
            groups.put(group.getName(), group);
        }
        
        return groups;
    }
    
    /**
     * Return Group object if existing into data
     * 
     * @param groupname
     * @return
     */
    public Group getGroup(String groupname) {
        HashMap<String, Group> groups = getGroups(); 
        return groups.get(groupname);
    }

}
