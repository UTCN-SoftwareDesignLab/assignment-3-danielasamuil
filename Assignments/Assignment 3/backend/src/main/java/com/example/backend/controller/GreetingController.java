package com.example.backend.controller;

import com.example.backend.service.PatientService;
import com.example.backend.websocket.Greeting;
import com.example.backend.websocket.PatientArrivedMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    PatientService patientService;

    @MessageMapping("/hello")
    @SendTo("/check-in/greetings")
    public Greeting greeting(PatientArrivedMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("You have a consultation at: " + HtmlUtils.htmlEscape(message.getDateOfConsultation()) + ", patient's name is: " + HtmlUtils.htmlEscape(patientService.findById(message.getPatientId()).getName()));
    }
}
