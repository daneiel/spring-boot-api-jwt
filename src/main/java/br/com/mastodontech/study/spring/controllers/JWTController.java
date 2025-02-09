package br.com.mastodontech.study.spring.controllers;

import br.com.mastodontech.study.spring.dto.ExampleDTO;
import br.com.mastodontech.study.spring.dto.JWEExampleDTO;
import br.com.mastodontech.study.spring.services.impl.JWTServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/jwt")
@Slf4j
public class JWTController {
    @Autowired
    private JWTServiceImpl jwtService;

    @PostMapping(value = "/encrypt-jwe")
    public ResponseEntity<?> consume(@RequestBody @Valid ExampleDTO exampleDTO) {
        try {
            JWEExampleDTO jweExampleDTO = exampleDTO.getJweExampleDTO();
            String encryptMessage = jwtService.encryptJwe(jweExampleDTO);
            jweExampleDTO.setMessage(encryptMessage);

            exampleDTO.setJweExampleDTO(jweExampleDTO);
            return ResponseEntity
                    .ok(exampleDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/decrypt-jwe")
    public ResponseEntity<?> createJwe(@RequestBody @Valid ExampleDTO exampleDTO) {
        log.info("start createJwe");
        try {
            JWEExampleDTO jweExampleDTO = exampleDTO.getJweExampleDTO();
            jweExampleDTO = jwtService.decryptJwe(jweExampleDTO.getMessage());

            exampleDTO.setJweExampleDTO(jweExampleDTO);
            return ResponseEntity.ok(exampleDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(value = "/jwk")
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
