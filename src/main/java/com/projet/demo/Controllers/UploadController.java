package com.projet.demo.Controllers;

import com.projet.demo.Models.AudiPFE;
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

    //TODO methode to remove after test
@GetMapping("")
    void test(){
    uploadService.test();
}

@PostMapping("")
    public ResponseEntity upload(@RequestPart("file")MultipartFile file, @RequestPart("request") AudiPFE audiPFE){
    Map<String,Object> response=uploadService.storeAndIndex(file,audiPFE);
   System.out.println(audiPFE);

    return new ResponseEntity(response,HttpStatus.OK);
}

}
