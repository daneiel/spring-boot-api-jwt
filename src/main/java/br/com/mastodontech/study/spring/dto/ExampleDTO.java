package br.com.mastodontech.study.spring.dto;

import lombok.Data;

@Data
public class ExampleDTO {
    private String name;
    private Integer age;

    private JWEExampleDTO jweExampleDTO;
}
