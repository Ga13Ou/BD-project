package com.projet.demo.Services;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity search(String key){
        SearchRequest searchRequest=new SearchRequest("bd_search_engine");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder=new MultiMatchQueryBuilder(key,"file","uploadedBy.nom");
        multiMatchQueryBuilder.type(MultiMatchQueryBuilder.Type.PHRASE_PREFIX);
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        Map<String,Object> response= new HashMap<String,Object>();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            response.put("status",0);
            response.put("body",hits.getHits());


            return new ResponseEntity(response,HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
           response.put("status",1);
           response.put("message","Problem connecting to the DB");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        }
    }
}
