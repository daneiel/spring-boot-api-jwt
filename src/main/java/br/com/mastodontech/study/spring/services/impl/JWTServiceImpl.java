package br.com.mastodontech.study.spring.services.impl;

import br.com.mastodontech.study.spring.dto.JWEExampleDTO;
import br.com.mastodontech.study.spring.services.SecurityJWT;
import br.com.mastodontech.study.spring.utils.GsonUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServiceImpl implements SecurityJWT<JWEExampleDTO, Object> {
    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Override
    public String getPublicKeyAsJWT() throws Exception {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("exemplo.com")
                .subject("usuario")
                .build();

        // Cria o JWS (assinatura do JWT)
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSSigner signer = new RSASSASigner(privateKey);
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    @Override
    public Map<String, Object> getJweInfo() throws Exception {
        String modulus = Base64.getUrlEncoder().encodeToString(publicKey.getModulus().toByteArray());
        String exponent = Base64.getUrlEncoder().encodeToString(publicKey.getPublicExponent().toByteArray());

        Map<String, String> jwk = new HashMap<>();
        jwk.put("kty", "RSA");
        jwk.put("n", modulus);
        jwk.put("e", exponent);

        Map<String, Object> jweInfo = new HashMap<>();
        jweInfo.put("alg", "RSA-OAEP-256");
        jweInfo.put("enc", "A256GCM");
        jweInfo.put("jwk", jwk);

        return jweInfo;
    }

    @Override
    public String encryptJwe(JWEExampleDTO jweExampleDTO) throws Exception {
        RSAPublicKey pubKeyFromJWK = getPubKeyFromJWK();
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
        Payload payload = new Payload(GsonUtil.toJson(jweExampleDTO));
        JWEEncrypter encrypter = new RSAEncrypter(pubKeyFromJWK);
        JWEObject jweObject = new JWEObject(header, payload);
        jweObject.encrypt(encrypter);

        return jweObject.serialize();
    }

    @Override
    public JWEExampleDTO decryptJwe(String jwe) throws Exception {
        JWEObject jweObject = JWEObject.parse(jwe);
        JWEDecrypter decrypter = new RSADecrypter(privateKey);
        jweObject.decrypt(decrypter);

        return GsonUtil.fromJson(
                jweObject.getPayload().toString(),
                JWEExampleDTO.class);
    }

    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private RSAPublicKey getPubKeyFromJWK() throws Exception {
        Map<String, Object> jweInfo = getJweInfo();
        String modulus = (String) jweInfo.get("n");
        String exponent = (String) jweInfo.get("e");

        byte[] modulusBytes = Base64.getUrlDecoder().decode(modulus);
        byte[] exponentBytes = Base64.getUrlDecoder().decode(exponent);

        BigInteger modulusBigInt = new BigInteger(1, modulusBytes);
        BigInteger exponentBigInt = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulusBigInt, exponentBigInt);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }
}
