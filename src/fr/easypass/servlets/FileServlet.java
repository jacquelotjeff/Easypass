package fr.easypass.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.easypass.utils.Encryptor;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet(name = "FileServlet", description = "File Downloader Servlet", urlPatterns = {FileServlet.URL_BASE + ""})
public class FileServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    public static final String URL_BASE = "/fichier";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
        String fileName = request.getParameter("nom");
        
        InputStream stream = Encryptor.class.getClassLoader().getResourceAsStream("config.properties");
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
        String uploadPath = jsonObject.get("uploader").getAsJsonObject().get("uploadDirPath").getAsString();
        
        String filePath = uploadPath + File.separator + fileName;
        
        File downloadFile = new File(filePath);
        
        String parentPath = downloadFile.getParent();
        
        //Vérifie que le fichier à envoyer fait bien parti du dossier "upload"
        if (parentPath.equals(uploadPath)) {
            
            FileInputStream inStream = new FileInputStream(downloadFile);
            
            // obtains ServletContext
            ServletContext context = getServletContext();
             
            // gets MIME type of the file
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {        
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
             
            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());
             
            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
             
            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();
             
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
             
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
             
            inStream.close();
            outStream.close();
            
        }
        
	}

}
