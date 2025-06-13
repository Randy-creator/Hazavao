package hei.school.hazavao.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGPTService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String apiUrl = "https://api.openai.com/v1/chat/completions";

  @Value("${openai.api.key}")
  private String apiKey;

  public String getDefinition(String word) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    Map<String, Object> message =
        Map.of("role", "user", "content", "Donne-moi une définition du mot : " + word);

    Map<String, Object> body =
        Map.of("model", "gpt-3.5-turbo", "messages", List.of(message), "max_tokens", 100);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    try {
      ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

      List<Map<String, Object>> choices =
          (List<Map<String, Object>>) response.getBody().get("choices");
      Map<String, Object> messageContent = (Map<String, Object>) choices.get(0).get("message");

      return (String) messageContent.get("content");
    } catch (Exception e) {
      e.printStackTrace();
      return "Erreur lors de la récupération de la définition.";
    }
  }
}
