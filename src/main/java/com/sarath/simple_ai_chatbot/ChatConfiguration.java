package com.sarath.simple_ai_chatbot;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "chat-service")
@Configuration(proxyBeanMethods = false)
@Data
public class ChatConfiguration {

    private String location;
    private String projectId;
    private String modelName;

    @Bean
    public VertexAI vertexAI(){
        return new VertexAI(projectId, location);
    }

    @Bean
    public GenerativeModel generativeModel(VertexAI vertexAI){
        return new GenerativeModel(modelName, vertexAI());
    }

}
