package fr.easypass.manager;

import java.util.HashMap;


import fr.easypass.model.Group;

public class GroupManager {
    
    private HashMap<String, Group> groups;
    
    public GroupManager()
    {
        this.groups = createGroups();
    }

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
    
    private HashMap<String, Group> createGroups() {
        
        
        HashMap<String, Group> groups = new HashMap<>();
         
        for (String[] groupInfos : getDatas()) {
            Group group = new Group();
            
            group.setName(groupInfos[0]);
            group.setDescription(groupInfos[1]);
            group.setLogo(groupInfos[2]);
            
            //Add group users
            String[] members = groupInfos[3].split("\\|");
            
            for (String username : members) {
                group.addUserName(username);
            }
            
            //Add group administrators
            String[] administrators = groupInfos[4].split("\\|");
            
            for (String username : administrators) {
                group.addAdministratorName(username);
            }
            
            groups.put(group.getName(), group);
        }
        
        return groups;
    }
    
    public HashMap<String, Group> getGroups() {
        return this.groups;
    }
    
    /**
     * Return Group object if existing into data
     * 
     * @param groupname
     * @return
     */
    public Group getGroup(String groupname) {
        return this.groups.get(groupname);
    }

}
