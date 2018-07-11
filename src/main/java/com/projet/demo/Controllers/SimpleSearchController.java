package com.projet.demo.Controllers;

import com.projet.demo.Services.SearchService;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/SimpleSearch")
public class SimpleSearchController {
    @Autowired
    SearchService searchService;
    @GetMapping("/{search}")
    public ResponseEntity search(@PathVariable String search){
        Map<String,Object> resp=(Map<String,Object>)searchService.search(search).getBody();
        return new ResponseEntity(resp,HttpStatus.OK);

    }

}
