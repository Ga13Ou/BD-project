package com.projet.demo.Services.ExtraAlgos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExtraAlgos {
@Autowired
ObjectMapper objectMapper;
    //get an object when the key is like this "key1.key2.key3"
    public String getDeepKeyFromMap(Map<String,Object> map,String compositeKey){
        String [] deepKey=compositeKey.split("\\.");    //escaping the "." cause it  means any char in regex
        if(deepKey.length==1){
            return (String) map.get(deepKey[0]);
        }
        for(int i=0;i<deepKey.length-1;i++){
            if(map.get(deepKey[i])==null){
                return null;
            }
            else{
                map= (Map<String, Object>) map.get(deepKey[i]);
            }
        }

        return (String) map.get(deepKey[deepKey.length-1]);
    }

}
