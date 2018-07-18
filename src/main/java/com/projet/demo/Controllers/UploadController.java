package com.projet.demo.Controllers;

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

    //TODO methode to remove after test
@GetMapping("")
    void test(){
    uploadService.test();
}

@PostMapping("")
    public ResponseEntity upload(@RequestPart("file")MultipartFile file, @RequestPart("request") Stage stage){
    Map<String,Object> response=uploadService.storeAndIndex(file, stage);
   System.out.println(stage);

    return new ResponseEntity(response,HttpStatus.OK);
}

@PostMapping("/BD_Docs")
    public ResponseEntity addDoc(@RequestPart("file")MultipartFile file, @RequestPart("request") Map<String,Object> request){
    String requestType=(String)request.get("docType");
    if(requestType!=null &&!file.isEmpty()&& !requestType.isEmpty()){
        if(requestType.equalsIgnoreCase("stage")){
            Stage tmp=objectMapper.convertValue(request.get("requestBody"),Stage.class);
            Map<String,Object> response=uploadService.addStage(tmp,file);
            return new ResponseEntity(response,HttpStatus.OK);
        }
        else if(requestType.equalsIgnoreCase("projet")){
            Projet tmp=objectMapper.convertValue(request.get("requestBody"),Projet.class);
            Map<String,Object> response=uploadService.addProjet(tmp,file);
            return new ResponseEntity(response,HttpStatus.OK);
        }
        else if(requestType.equalsIgnoreCase("evenement")){
            Evenement tmp=objectMapper.convertValue(request.get("requestBody"),Evenement.class);
            Map<String,Object> response=uploadService.addEvenement(tmp,file);
            return new ResponseEntity(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
}

}
