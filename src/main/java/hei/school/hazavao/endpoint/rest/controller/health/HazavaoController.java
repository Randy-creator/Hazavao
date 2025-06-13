package hei.school.hazavao.endpoint.rest.controller.health;

import hei.school.hazavao.service.ChatGPTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazavaoController {
  private final ChatGPTService chatGPTService;

  public HazavaoController(ChatGPTService chatGPTService) {
    this.chatGPTService = chatGPTService;
  }

  @GetMapping("/hazavao")
  public String hazavao(@RequestParam String mot) {
    return chatGPTService.getDefinition(mot);
  }
}
