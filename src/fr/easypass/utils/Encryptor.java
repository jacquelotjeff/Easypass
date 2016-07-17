package fr.easypass.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.jasypt.util.text.BasicTextEncryptor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Encryptor {
    
    String encryptedPassword;
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    String password;
    
    public Encryptor() {
        
        InputStream stream = Encryptor.class.getClassLoader().getResourceAsStream("config.properties");
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
        this.password = jsonObject.get("encryptor").getAsJsonObject().get("password").getAsString();
        
    }
    
    public void setPlainPassword(String plainPassword){
        
        textEncryptor.setPassword(this.password);
        this.encryptedPassword = textEncryptor.encrypt(plainPassword);
        
    }
    
    public String getEncryptedPassword(){
        
        return this.encryptedPassword;
        
    }
    
    public String decryptPassword(String password){
        
        textEncryptor.setPassword(this.password);
        return textEncryptor.decrypt(password);
        
    }

}
