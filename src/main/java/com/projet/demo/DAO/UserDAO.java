package com.projet.demo.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.Models.User;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserDAO {
    private final String INDEX = "person";
    private final String TYPE = "person";

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ObjectMapper objectMapper;

    public User insertUser(User u) {
        u.setId(UUID.randomUUID().toString());
        Map dataMap = objectMapper.convertValue(u, Map.class);

        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, u.getId()).source(dataMap);


        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
            //TODO remove this log after test
            System.out.println("this is the id: "+u.getId());
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
            e.printStackTrace();

        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return u;
    }

    public Map<String, Object> getUserById(String id){
        GetRequest getRequest=new GetRequest(INDEX,TYPE,id);
        GetResponse getResponse=null;

        try {
            getResponse=restHighLevelClient.get(getRequest);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap;
        if(getResponse.isExists()) {
            sourceAsMap = getResponse.getSourceAsMap();
        }
        else{
            sourceAsMap = new HashMap<String,Object>();
            sourceAsMap.put("Error",1);
            sourceAsMap.put("Message","id not found");


        }
        return sourceAsMap;
    }


}
