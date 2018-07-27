package com.projet.demo.Services;

import com.projet.demo.DAO.EvenementDAO;
import com.projet.demo.DAO.ProjetDAO;
import com.projet.demo.DAO.StageDAO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    StageDAO stageDAO;
    @Autowired
    ProjetDAO projetDAO;
    @Autowired
    EvenementDAO evenementDAO;
    @Value("${ES.AppIndex}")
    String INDEX;

    /**
     * this is the search service method, it redirect the search request to the right DAO
     * @param data
     * @return
     */
    public ResponseEntity fullSearch(Map<String,Object> data){
        if(data.get("type")!=null &&((String)data.get("type")).equalsIgnoreCase("stage"))
            return stageDAO.fullSearchStage((Map<String,Object>)data.get("data"));
        if(data.get("type")!=null &&((String)data.get("type")).equalsIgnoreCase("projet"))
            return projetDAO.fullSearchProjet((Map<String,Object>)data.get("data"));
        if(data.get("type")!=null &&((String)data.get("type")).equalsIgnoreCase("evenement"))
            return evenementDAO.fullSearchEvenement((Map<String,Object>)data.get("data"));
        return new ResponseEntity("Bad Request",HttpStatus.BAD_REQUEST);
    }


}
