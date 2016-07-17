package fr.easypass.utils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileUploader {
    
    public static final String UPLOAD_DIRECTORY = "upload";
    private static final List<String> ALLOWED_MIME_TYPE = Arrays.asList(new String[]{"image/jpeg", "image/png", "image/gif"});
    private static final double MAX_MEGABYTES_FILE_SIZE = 2;
    public static final Logger log = Logger.getLogger(FileUploader.class.getName());
    
    /**
     * Iterates over form enc-type / place the classic fields inside Map<FieldName, FieldValue> / Upload files field if valid. 
     * @param request
     * @return Return a Map<FieldName, FieldValue> (Simple fields),
     *         Map<fieldName, List<String> (Fields multiple values), 
     *         Map<FieldName, UploadedFilePath> (File field) ,
     *         Map<errors, List<String>> (File field)
     */
    public static Map<String, Object> uploadPicture(HttpServletRequest request){
        
        Map<String, Object> result = new HashMap<>(); 
        
        List<String> errors = new ArrayList<String>();
        
        Boolean isMultiPart  = ServletFileUpload.isMultipartContent(request);
        Boolean isValidFile = true;
        
        if (isMultiPart) {
            
            DiskFileItemFactory factory = new DiskFileItemFactory();
            
            ServletContext  servletContext = request.getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            InputStream stream = Encryptor.class.getClassLoader().getResourceAsStream("config.properties");
            JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
            String uploadPath = jsonObject.get("uploader").getAsJsonObject().get("uploadDirPath").getAsString();
            
            File uploadDir = new File(uploadPath);
            
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            try {
                
                List<FileItem> formItems = upload.parseRequest(request);
     
                if (formItems != null && formItems.size() > 0) {
                    // iterates over form's fields
                    for (FileItem item : formItems) {
                        // processes only fields that are not form fields
                        if (!item.isFormField()) {
                            
                            File file = new File(item.getName());
                            
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            
                            String fileName = sdf.format(date) + '-' + file.getName();
                            
                            
                            //If no file, just return without the fieldName inside result (For edit form)
                            if (!fileName.equals("")) {
                            	
                                Long megabytes = item.getSize() / 1024 / 1024;
                            	if (megabytes > MAX_MEGABYTES_FILE_SIZE) {
                                    errors.add("Le fichier est trop lourd et ne doit pas dépassé " + MAX_MEGABYTES_FILE_SIZE + " mb.");
                                    isValidFile = false;
                                }
                                
                                String mimeType = servletContext.getMimeType(fileName);
                                
                                if (!ALLOWED_MIME_TYPE.contains(mimeType)) {
                                    
                                    errors.add("Le fichier doit être une image.");
                                    isValidFile = false;
                                }
                                
                                if (isValidFile) {
                                    
                                    String filePath = uploadPath + File.separator + fileName;
                                    File storeFile = new File(filePath);
                                    
                                    result.put(item.getFieldName(), fileName);
             
                                    // saves the file on disk
                                    item.write(storeFile);
                                    
                                } else {
                                    
                                    result.put("errors", errors);
                                }
                            }
                            
                        } else {
                            
                            String fieldName = item.getFieldName(); 
                            //If a field has multiple value, this iterates on the same fieldName but not the same field value.
                            if (result.containsKey(fieldName)) {
                                
                                //A field can have multiple value
                                if (result.get(fieldName) instanceof List) {
                                    
                                    List<String> values = new ArrayList<String>();
                                    
                                    values.addAll((List) result.get(fieldName));
                                    values.add(item.getString("UTF-8"));
                                    
                                    result.put(fieldName, values);
                                    
                                } else {
                                    
                                    List<String> values = new ArrayList<String>();
                                    values.add((String) result.get(fieldName));
                                    values.add(item.getString("UTF-8"));
                                    result.put(fieldName, values);
                                    
                                }
                                
                            } else {
                                
                                result.put(item.getFieldName(), item.getString("UTF-8"));
                                
                            }
                            
                        }
                    }
                }
            } catch (Exception ex) {
            	log.log(Level.SEVERE, "Error while uploading a file", ex);
            }
        }
        
        return result;
    }
}
