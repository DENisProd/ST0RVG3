package ru.darksecrets.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1")
@CrossOrigin(value = "*")
@RestController
public class HeathController {
    @GetMapping
    public ResponseEntity<?> getHeath () {
        return ResponseEntity.ok("pong");
    }
}
