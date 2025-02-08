package br.com.mastodontech.study.spring.controllers;

import br.com.mastodontech.study.spring.dto.ExampleDTO;
import br.com.mastodontech.study.spring.dto.JWEExampleDTO;
import br.com.mastodontech.study.spring.services.impl.JWTServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/jwt")
@Slf4j
public class JWTController {
    @Autowired
    private JWTServiceImpl jwtService;

    @PostMapping(value = "/consume")
    public ResponseEntity<String> consume(@RequestBody @Valid ExampleDTO exampleDTO) {
        try {
            JWEExampleDTO jweExampleDTO = exampleDTO.getJweExampleDTO();
            String response = jwtService.encryptJwe(jweExampleDTO);
            return ResponseEntity
                    .ok(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(value = "/create-jwe")
    public ResponseEntity<String> createJwe(@RequestBody JWEExampleDTO jWEExampleDTO) {
        log.info("start createJwe");
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(value = "/pub-key")
    public ResponseEntity<?> getPubKey() {
        log.info("start getPubKey");
        try {
            Map<String, Object> response = jwtService.getJweInfo();
            log.info("end getPubKey");

            return ResponseEntity
                    .ok(response);
        } catch (Exception e) {
            log.info("end getPubKey");
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
