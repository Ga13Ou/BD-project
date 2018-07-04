package com.projet.demo.Services;

import com.projet.demo.DAO.UploadDAO;
import com.projet.demo.Models.AudiPFE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UploadService {
    @Value("${upload.dir}")
    private String UPLOAD_FOLDER;

    @Autowired
    private UploadDAO uploadDAO;

    //TODO methode to remove after test
    public void test() {
        java.io.File file = new java.io.File(".");
        System.out.println(UPLOAD_FOLDER);

    }

    public Map<String, Object> store(MultipartFile file) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (file.isEmpty()) {
            response.put("status", 1);
            response.put("Message", "no file uploaded");
            return response;
        } else {
            byte[] bytes;
            try {
                //storing the file
                bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                System.out.println(path);
                response.put("status", 0);
                response.put("message", "successful upload");
            } catch (IOException e) {
                e.printStackTrace();
                response.put("status", 2);
                response.put("message", "problem uploading the file");

            }

        }

        return response;

    }
    public Map<String,Object> storeAndIndex(MultipartFile file, AudiPFE audiPFE){
        Map< String,Object> response=store(file);
        if(response.get("status").equals(new Integer(0))){
            audiPFE.setDocumentName( file.getOriginalFilename());
            audiPFE.setDocumentType(file.getContentType());
            audiPFE.setDocumentPath(UPLOAD_FOLDER + file.getOriginalFilename());    //TODO change when I pass to a remote storage location
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                String encodedFile=Base64Utils.encodeToString(bytes);
                audiPFE.setDocumentContent(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        response.put("ES_Response",uploadDAO.indexDocument(audiPFE));   //TODO remove the base64 content in the response
        return response;
    }
}
