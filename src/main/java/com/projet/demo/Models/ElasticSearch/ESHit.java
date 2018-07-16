package com.projet.demo.Models.ElasticSearch;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;


import java.util.*;

public class ESHit {
    private float _score;
    private Map<String,ArrayList<String>> highlights=new HashMap<String, ArrayList<String>>();
    private Map<String,Object> sourceAsMap=new HashMap<String,Object>();

    public float get_score() {
        return _score;
    }

    public void set_score(SearchHit hit) {
        this._score = hit.getScore();
    }

    public Map<String, Object> getSourceAsMap() {
        return sourceAsMap;
    }

    public void setSourceAsMap(SearchHit hit) {
        this.sourceAsMap = hit.getSourceAsMap();
    }
    public void setHighlights(SearchHit hit){
        for(String key: hit.getHighlightFields().keySet()){
            this.highlights.put(key,new ArrayList<String>());
            for(Text fragment: hit.getHighlightFields().get(key).getFragments()){
                this.highlights.get(key).add(fragment.toString());
            }
        }
    }
    public Map<String,ArrayList<String>> getHighlights(){
        return this.highlights;
    }

    public void init(SearchHit hit){
        this.setSourceAsMap(hit);
        this.setHighlights(hit);
        this.set_score(hit);

    }
}
