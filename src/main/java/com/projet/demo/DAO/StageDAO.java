package com.projet.demo.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.ElasticSearch.ESHit;
import com.projet.demo.Models.Stage;
import com.projet.demo.Services.ExtraAlgos.ExtraAlgos;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

/**
 * this class is the Data Access Object for the model Stage
 */
@Repository
public class StageDAO {
    @Value("${ES.AppIndex}")
    private String INDEX;
    private final String TYPE = "students_docs";

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExtraAlgos extraAlgos;

    public ResponseEntity indexDocument(Stage stage) {
        stage.set_id(UUID.randomUUID().toString());
        Map dataMap = objectMapper.convertValue(stage, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, stage.get_id()).source(dataMap);
        indexRequest.setPipeline("attachment");
        IndexResponse indexResponse=null;
        try {
             indexResponse = restHighLevelClient.index(indexRequest);
            System.out.println(indexResponse);

            return new ResponseEntity(indexRequest,HttpStatus.OK);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
            e.printStackTrace();
            return new ResponseEntity(indexRequest,HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
            ex.getLocalizedMessage();
            return new ResponseEntity(indexRequest,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * this is the method that executes the search for the Stage documents
     * @param data
     * @return
     */
    public ResponseEntity fullSearchStage(Map<String, Object> data) {
        /**
         * matchArray contains fields we want to match
         * termArray contains fields on which we want to excute a termSearch request
         */
        String[] matchArray = {"intituleSujet", "objectifProjet",
                "contexteProblematique", "retombeesAttendues",  "attachment.content","attachment.title"};
        String[] termArray = {"type", "etablissement", "candidat.nom", "candidat.prenom", "domainePrincipal"
                , "technologie", "encadreurUniversitaire", "encadreurEntreprise", "uploadedBy.nom", "uploadBy.prenom","note"};
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder bool = new BoolQueryBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String a : matchArray) {
            if (extraAlgos.getDeepKeyFromMap(data, a) != null) {
                MatchPhrasePrefixQueryBuilder matchQuery = new MatchPhrasePrefixQueryBuilder(a, extraAlgos.getDeepKeyFromMap(data, a));
                bool.must(matchQuery);
                highlightBuilder.field(a, 500, 4);

            }
        }
        for (String b : termArray) {
            if (extraAlgos.getDeepKeyFromMap(data, b) != null) {
                TermQueryBuilder termQuery = new TermQueryBuilder(b, extraAlgos.getDeepKeyFromMap(data, b));
                bool.filter(termQuery);
                //TODO check for "note" attribute to convert it to int if it causes problem as a String
            }
        }
        searchSourceBuilder.query(bool);
        String[] excludeFields = new String[]{"attachment.content","file"};
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
