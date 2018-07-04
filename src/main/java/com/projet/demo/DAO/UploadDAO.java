package com.projet.demo.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.AudiPFE;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Repository
public class UploadDAO {
    private final String INDEX = "files";
    private final String TYPE = "documents";

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ObjectMapper objectMapper;

    public AudiPFE indexDocument(AudiPFE audiPFE){
        audiPFE.setId(UUID.randomUUID().toString());
        Map dataMap = objectMapper.convertValue(audiPFE, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, audiPFE.getId()).source(dataMap);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
            //TODO remove this log after test
            System.out.println("this is the id: "+audiPFE.getId());
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();

        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return audiPFE;


    }
}
