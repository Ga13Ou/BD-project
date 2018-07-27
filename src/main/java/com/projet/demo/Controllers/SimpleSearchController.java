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

    /**
     * this is the search method that is responsible of catching all search request
     * the mapping to access this POST methode is {baseURL}/SimpleSearch/Advanced
     *
     * @param data the JSON object sent to this method must be in this form:
     *             {
     *             "type":"example",
     *             "data":{
     *             "searchField1":"searchValue1",
     *             "searchField2":"searchValue2",
     *             .
     *             .
     *             .
     *             "searchFieldN":"searchValueN"
     *
     *             }
     *             }
     * @return  the method returns a array containing all the hits including their scores and highlights
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/Advanced")
    public ResponseEntity advancedSearch(@RequestBody Map<String, Object> data) {
        return searchService.fullSearch(data);
    }

}
