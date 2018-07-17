package com.projet.demo.DAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.ElasticSearch.ESHit;
import com.projet.demo.Models.Projet;
import com.projet.demo.Models.Stage;
import com.projet.demo.Services.ExtraAlgos.ExtraAlgos;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProjetDAO {
    private final String INDEX = "bd_search_engine";
    private final String TYPE = "business_docs";

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExtraAlgos extraAlgos;

    public Projet indexDocument(Projet projet){
        projet.set_id(UUID.randomUUID().toString());
        Map dataMap = objectMapper.convertValue(projet, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, projet.get_id()).source(dataMap);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
            //TODO remove this log after test
            System.out.println("this is the id: "+ projet.get_id());
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
            e.printStackTrace();
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        projet.setFile(null);
        return projet;


    }
    public ResponseEntity fullSearchProjet(Map<String, Object> data) {
        String[] matchArray = {"description", "file.content"}; //TODO change "file" attribute
        String[] termArray = {"type", "categorie", "client", "duree"
                , "chefDuProjet", "equipe", "uploadedBy.nom", "uploadedBy.prenom"};
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder bool = new BoolQueryBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String a : matchArray) {

            if (extraAlgos.getDeepKeyFromMap(data,a) != null) {
                MatchPhrasePrefixQueryBuilder matchQuery = new MatchPhrasePrefixQueryBuilder(a, extraAlgos.getDeepKeyFromMap(data,a));
                bool.must(matchQuery);
                highlightBuilder.field(a, 500, 4);

            }
        }
        for (String b : termArray) {
            if (extraAlgos.getDeepKeyFromMap(data,b) != null) {
                TermQueryBuilder termQuery = new TermQueryBuilder(b, extraAlgos.getDeepKeyFromMap(data,b));
                bool.filter(termQuery);
            }
        }


        searchSourceBuilder.query(bool);
        String[] excludeFields = new String[]{"file"};
        String[] includeFields = new String[]{};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        Map<String, Object> responseES = new HashMap<String, Object>();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            ArrayList<ESHit> responseHit = new ArrayList<ESHit>();
            //getting hits
            for (SearchHit hit : hits.getHits()) {
                ESHit tmp = new ESHit();
                tmp.init(hit);
                responseHit.add(tmp);
            }
            //preparing response
            responseES.put("status", 0);
            responseES.put("body", responseHit);
            //sending response
            return new ResponseEntity(responseES, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            responseES.put("status", 1);
            responseES.put("message", "Problem connecting to the DB");
            return new ResponseEntity<Map<String, Object>>(responseES, HttpStatus.OK);
        }

    }
}
