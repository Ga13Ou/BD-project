package com.projet.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

/**
 * this class contains the configuration of the elasticsearch high level client
 * server properties can be found in properties file
 */
@Configuration
public class ElasticSearchConifg extends AbstractFactoryBean {

    private static final Logger LOG=LoggerFactory.getLogger(ElasticSearchConifg.class);

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;
    private RestHighLevelClient restHighLevelCLient;

    @Value("${ES.BD.ADDRESS}")
    private String ServerAddress;

    @Override
    public void destroy(){
        try{
            if(restHighLevelCLient!=null){
                restHighLevelCLient.close();
            }
        } catch (final Exception e){
            LOG.error("Error closing ElasticSearch client", e);
        }
    }
    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }
    @Override
    public boolean isSingleton(){
        return false;
    }
    @Override
    protected RestHighLevelClient createInstance() throws Exception {
        return buildClient();
    }

    private RestHighLevelClient buildClient(){
        try{
            restHighLevelCLient= new RestHighLevelClient(RestClient.builder(
                    new HttpHost(ServerAddress,9200,"http"),
                    new HttpHost(ServerAddress,9201,"http")
            ));
        }catch(Exception e){
            LOG.error(e.getMessage());
        }
        return restHighLevelCLient;
    }
}
