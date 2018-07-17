package com.projet.demo.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DownloadService {
    @Value("${ES.AppIndex}")
    private String INDEX;
    @Autowired
    private RestHighLevelClient highLevelClient;
    @Autowired
    ObjectMapper objectMapper;
    public ResponseEntity getFileById(String id) {
        GetRequest getRequest=new GetRequest(INDEX,"_all",id);
        Map<String,String> response=new HashMap<String,String>();

        try {
            GetResponse getResponse=highLevelClient.get(getRequest);
            if(getResponse.isExists()){
                System.out.println(getResponse.getSourceAsMap().get("path"));
                response.put("status","0");
                response.put("path", (String) getResponse.getSourceAsMap().get("path"));
                return new ResponseEntity(response,HttpStatus.OK);
            }
            else{
                response.put("status","1");
                response.put("message","wrong document id");
                return new ResponseEntity(response,HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.put("Status","2");
            response.put("message","Problem connecting to the Database");
            return new ResponseEntity(response,HttpStatus.SERVICE_UNAVAILABLE);

        }


    }
}
