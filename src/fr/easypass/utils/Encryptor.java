package fr.easypass.utils;

import org.jasypt.util.text.StrongTextEncryptor;

public class Encryptor {
    
    String encryptedPassword;
    StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
    
    public Encryptor() {
        
    }
    
    public void setPlainPassword(String plainPassword){
        
        textEncryptor.setPassword("password...");
        this.encryptedPassword = textEncryptor.encrypt(plainPassword);
        
    }
    
    public String getEncryptedPassword(){
        
        return this.encryptedPassword;
        
    }
    
    public String decryptPassword(String password){
        
        textEncryptor.setPassword("password...");
        return textEncryptor.decrypt(password);
        
    }

}
