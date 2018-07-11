package com.projet.demo.Services;

import com.projet.demo.DAO.EvenementDAO;
import com.projet.demo.DAO.ProjetDAO;
import com.projet.demo.DAO.StageDAO;
import com.projet.demo.Models.Evenement;
import com.projet.demo.Models.Projet;
import com.projet.demo.Models.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadService {
    @Value("${upload.dir}")
    private String UPLOAD_FOLDER;

    @Autowired
    private StageDAO stageDAO;
    @Autowired
    private ProjetDAO projetDAO;
    @Autowired
    private EvenementDAO evenementDAO;
    @Autowired
    private DocumentContentService documentContentService;

    //TODO methode to remove after test
    public void test() {
        java.io.File file = new java.io.File(".");
        System.out.println(UPLOAD_FOLDER);

    }

    public Map<String, Object> storeUtil(MultipartFile file, String savingPath) {
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
                Path path = Paths.get(savingPath);
                path.getParent().toFile().mkdirs();
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

    //this method is for local file upload testing
    public Map<String, Object> store(MultipartFile file) {
        return this.storeUtil(file, UPLOAD_FOLDER + file.getOriginalFilename());

    }

    public Map<String, Object> storeAndIndex(MultipartFile file, Stage stage) {
        Map<String, Object> response = store(file);
        if (response.get("status").equals(new Integer(0))) {
            stage.setPath(UPLOAD_FOLDER + file.getOriginalFilename());    //TODO change when I pass to a remote storage location
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                String encodedFile = Base64Utils.encodeToString(bytes);
                stage.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        response.put("ES_Response", stageDAO.indexDocument(stage));   //TODO remove the base64 content in the response
        return response;
    }

    public Map<String, Object> addStage(Stage stage, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "\\StageFiles\\" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            stage.setPath(UPLOAD_FOLDER + "\\StageFiles\\" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
               /* String encodedFile = Base64Utils.encodeToString(bytes);*/ //TODO use this before deploying to the server running mapper-attachment
                String encodedFile=documentContentService.getDocumentContent(bytes);
                stage.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("im here");
            response.put("ES_Response", stageDAO.indexDocument(stage));   //TODO remove the base64 content in the response
        }

        return response;

    }

    public Map<String, Object> addProjet(Projet projet, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "\\ProjetFiles\\" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            projet.setPath(UPLOAD_FOLDER + "\\ProjetFiles\\" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                /* String encodedFile = Base64Utils.encodeToString(bytes);*/ //TODO use this before deploying to the server running mapper-attachment
                String encodedFile=documentContentService.getDocumentContent(bytes);
                projet.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.put("ES_Response", projetDAO.indexDocument(projet));   //TODO remove the base64 content in the response
        }

        return response;

    }

    public Map<String, Object> addEvenement(Evenement evenement, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "\\EvenementFiles\\" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            evenement.setPath(UPLOAD_FOLDER + "\\EvenementFiles\\" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                /* String encodedFile = Base64Utils.encodeToString(bytes);*/ //TODO use this before deploying to the server running mapper-attachment
                String encodedFile=documentContentService.getDocumentContent(bytes);
                evenement.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.put("ES_Response", evenementDAO.indexDocument(evenement));   //TODO remove the base64 content in the response
        }

        return response;

    }
}
