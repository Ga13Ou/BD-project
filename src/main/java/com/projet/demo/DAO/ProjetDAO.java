package com.projet.demo.DAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.Projet;
import com.projet.demo.Models.Stage;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
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

        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return projet;


    }
}
