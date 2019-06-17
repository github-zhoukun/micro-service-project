package com.zk.sso.ssoservice.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt TOKEN 生成 解析
 *
 * @author zhoukun
 */
@Slf4j
public class JwtUtil {

    private JwtUtil() {

    }

    /**
     * 加密Key
     */
    public static final String SECRET = "c61070db-450b-419e-8ff9-e45fae4f6e3c";
    /**
     * 发行人
     */
    private static String ISSUER = "JackKun";


    /**
     * 生成Token
     *
     * @param claims     主体
     * @param expireDate 过期时间
     * @return
     */
    public static String generateToken(Map<String, String> claims, Date expireDate) {
        try {
            //申明加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            //创建JWT
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(expireDate);

            //设置主体参数
            claims.forEach((k, v) -> {
                builder.withClaim(k, v);
            });

            //返回加密签名 token
            return builder.sign(algorithm);
        } catch (IllegalArgumentException e) {
            log.error("生成Token 非法参数异常! errMsg:{}", e);
            throw new RuntimeException(e);

        } catch (JWTCreationException e) {
            log.error("生成Token 创建JWT异常! errMsg:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证Token
     *
     * @return
     */
    public static Map<String, String> verifyToken(String token) {
        //申明加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        Map<String, String> resultMap = new HashMap<>();
        Map<String, Claim> claimMap = jwt.getClaims();
        claimMap.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }


    public static void main(String[] args) {
        Map<String, String> ss = new HashMap<>();
        ss.forEach((k, v) -> System.out.println(k));

    }
}
