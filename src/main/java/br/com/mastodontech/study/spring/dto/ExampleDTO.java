package br.com.mastodontech.study.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExampleDTO {
    private String message;

    @JsonProperty(value = "jwe")
    private JWEExampleDTO jweExampleDTO;
}
