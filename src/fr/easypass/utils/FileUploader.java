package fr.easypass.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploader {
    
    private static final String UPLOAD_DIRECTORY = "upload";
    private static final List<String> ALLOWED_MIME_TYPE = Arrays.asList(new String[]{"image/jpeg", "image/png", "image/gif"});
    private static final double MAX_MEGABYTES_FILE_SIZE = 2;
    
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
            
            String uploadPath = servletContext.getRealPath("")  + File.separator + UPLOAD_DIRECTORY;
            
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
                            
                            String fileName = file.getName();
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
                                String path = storeFile.getAbsolutePath();
                                
                                result.put(item.getFieldName(), path);
         
                                // saves the file on disk
                                item.write(storeFile);
                                
                            } else {
                                
                                result.put("errors", errors);
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
                System.out.println(ex.getMessage());
            }
                    
        }
        
        return result;
    }
}
