package com.projet.demo.Controllers;

import com.projet.demo.Services.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    DownloadService downloadService;
    @Value("${upload.dir}")
    private String UPLOAD_FOLDER;

    @GetMapping("/{id}")
    @CrossOrigin(origins="*")
    public ResponseEntity downloadFileById(@PathVariable String id) {

        ResponseEntity result = downloadService.getFileById(id);
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        if (resultBody.get("status").equals("0")) {
            String path = resultBody.get("path");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");


            try {
                File file = new File(path);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                file.length();
                headers.add("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                return ResponseEntity.ok().contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream")).headers(headers).body(resource);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return result;
        }

    }
}
