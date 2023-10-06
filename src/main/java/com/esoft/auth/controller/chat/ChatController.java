package com.esoft.auth.controller.chat;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@SecurityRequirement(name = "esoft-api")
public class ChatController {
}
