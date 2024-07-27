package com.sarath.simple_ai_chatbot;

import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gemini")
@RequiredArgsConstructor
public class ChatGeminiController {

    private final GenerativeModel generativeModel;
    @Autowired private ChatService chatService;

    @GetMapping
    public ResponseEntity<String> heartBeat(){
        return ResponseEntity.ok("Service is UP !!");
    }

    @PostMapping("/multipart")
    public String file(@RequestParam(value = "file", required = false) MultipartFile file, String query) throws IOException {
        if(file == null || file.isEmpty()){
            return chatService.chatDiscussion(null,null,null, query) ;
        }
        GenerateContentResponse generateContentResponse = this.generativeModel.generateContent(
                ContentMaker.fromMultiModalData(PartMaker.fromMimeTypeAndData(file.getContentType(),
                        file.getBytes()), query)
        );
        return ResponseHandler.getText(generateContentResponse);
    }

    @GetMapping("/chat")
    public ResponseEntity<String> simpleGeminiChat(String query){
        return ResponseEntity.ok(chatService.chatDiscussion(null,null,null, query)) ;
    }

}
