package com.projet.demo.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.Evenement;
import com.projet.demo.Models.Projet;
import com.projet.demo.Models.Stage;
import com.projet.demo.Services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload")

public class UploadController {

    @Autowired
    UploadService uploadService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * this method provides the upload service to the frontEnd
     * to use it you have to send a multipart request to this link: {baseURL}/upload/BD_Docs
     * the request must contain the document you want to upload with the id "file"
     * and a .JSON file containing the actual data about the uploaded file with the id "request"
     * the .JSON file must be of this format:
     * {
     * "docType":"example",   //can be stage, evenement or projet
     * "requestBody": {
     * "dataField1":"dataValue1",
     * "dataField2":"dataValue2",
     * .
     * .
     * .
     * "dataFieldN":"dataValueN"
     * }
     * needed fields of each type can be found in model classes
     *
     * }
     *
     * @param file
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/BD_Docs")
    public ResponseEntity addDoc(@RequestPart("file") MultipartFile file, @RequestPart("request") Map<String, Object> request) throws JsonProcessingException {
        String requestType = (String) request.get("docType");
        if (requestType != null && !file.isEmpty() && !requestType.isEmpty()) {
            if (requestType.equalsIgnoreCase("stage")) {
                System.out.println(objectMapper.writeValueAsString(request));
                Stage tmp = objectMapper.convertValue(request.get("requestBody"), Stage.class);
                Map<String, Object> response = uploadService.addStage(tmp, file);
                return new ResponseEntity(response, HttpStatus.OK);
            } else if (requestType.equalsIgnoreCase("projet")) {
                Projet tmp = objectMapper.convertValue(request.get("requestBody"), Projet.class);
                Map<String, Object> response = uploadService.addProjet(tmp, file);
                return new ResponseEntity(response, HttpStatus.OK);
            } else if (requestType.equalsIgnoreCase("evenement")) {
                Evenement tmp = objectMapper.convertValue(request.get("requestBody"), Evenement.class);
                Map<String, Object> response = uploadService.addEvenement(tmp, file);
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
