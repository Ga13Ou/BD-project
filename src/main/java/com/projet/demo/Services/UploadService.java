package com.projet.demo.Services;

import com.projet.demo.DAO.EvenementDAO;
import com.projet.demo.DAO.ProjetDAO;
import com.projet.demo.DAO.StageDAO;
import com.projet.demo.Models.Evenement;
import com.projet.demo.Models.Projet;
import com.projet.demo.Models.Stage;
import com.projet.demo.Services.ExtraAlgos.ExtraAlgos;
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
    private ExtraAlgos extraAlgos;

    /**
     * this method is used to upload a certain file to a certain path
     * @param file
     * @param savingPath
     * @return
     */

    public Map<String, Object> storeUtil(MultipartFile file, String savingPath) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (file.isEmpty()) {
            response.put("status", 1);
            response.put("Message", "no file uploaded");
            return response;
        } else if (!extraAlgos.isAllowed(file)){
            response.put("status", 3);
            response.put("Message", "this type of files is not allowed");
            return response;
        }
        else{
            byte[] bytes;
            //TODO add a unique ID to the file name while uploading it so that it doesn't create a problem if two files have the same name

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


    /**
     * this method is used to index the document in elasticSearch using the right DAO
     * @param stage
     * @param file
     * @return
     */
    public Map<String, Object> addStage(Stage stage, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "/StageFiles/" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            stage.setPath(UPLOAD_FOLDER + "/StageFiles/" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                String encodedFile = Base64Utils.encodeToString(bytes);
                /*String encodedFile=documentContentService.getDocumentContent(bytes);*/
                stage.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.put("ES_Response", stageDAO.indexDocument(stage));
        }

        return response;

    }

    /**
     * this method is used to index the document in elasticSearch using the right DAO
     * @param projet
     * @param file
     * @return
     */
    public Map<String, Object> addProjet(Projet projet, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "/ProjetFiles/" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            projet.setPath(UPLOAD_FOLDER + "/ProjetFiles/" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                String encodedFile = Base64Utils.encodeToString(bytes);
                /*String encodedFile=documentContentService.getDocumentContent(bytes);*/
                projet.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.put("ES_Response", projetDAO.indexDocument(projet));
        }

        return response;

    }

    /**
     * this method is used to index the document in elasticSearch using the right DAO
     * @param evenement
     * @param file
     * @return
     */
    public Map<String, Object> addEvenement(Evenement evenement, MultipartFile file) {
        Map<String, Object> response = storeUtil(file, UPLOAD_FOLDER + "/EvenementFiles/" + file.getOriginalFilename());
        if (response.get("status").equals(new Integer(0))) {
            evenement.setPath(UPLOAD_FOLDER + "/EvenementFiles/" + file.getOriginalFilename());
            byte[] bytes;
            try {
                //Encoding the file to base 64
                bytes = file.getBytes();
                String encodedFile = Base64Utils.encodeToString(bytes);
                /*String encodedFile=documentContentService.getDocumentContent(bytes);*/
                evenement.setFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.put("ES_Response", evenementDAO.indexDocument(evenement));
        }

        return response;

    }
}
