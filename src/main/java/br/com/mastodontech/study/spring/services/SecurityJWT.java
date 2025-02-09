package br.com.mastodontech.study.spring.services;

import java.security.PrivateKey;
import java.util.Map;

public interface SecurityJWT <ObjToEncrypt> {
    public Map<String, Object> getJweInfo() throws Exception;
    public String encryptJwe(ObjToEncrypt objToEncrypt) throws Exception;
    public ObjToEncrypt decryptJwe(String jwe) throws Exception;
}
