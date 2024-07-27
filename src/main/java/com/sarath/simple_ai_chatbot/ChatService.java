package com.sarath.simple_ai_chatbot;


import java.io.IOException;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ChatService {

    @Autowired private ChatConfiguration chatConfiguration;

    public String chatDiscussion(String projectId, String location, String modelName, String query) {

        try (VertexAI vertexAI = new VertexAI(!StringUtils.hasText(projectId) ? chatConfiguration.getProjectId() : projectId,
                !StringUtils.hasText(location) ? chatConfiguration.getLocation() : location)) {
            GenerateContentResponse response;
            GenerativeModel model = new GenerativeModel(!StringUtils.hasText(modelName) ? chatConfiguration.getModelName() : modelName, vertexAI);
            // Create a chat session to be used for interactive conversation.
            ChatSession chatSession = new ChatSession(model);
            response = chatSession.sendMessage(query);
            return ResponseHandler.getText(response);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
