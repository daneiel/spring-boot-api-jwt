package br.com.mastodontech.study.spring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWEExampleDTO {
    private String message;
}
