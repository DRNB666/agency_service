package com.leepsmart.code.common.utils.encrypt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt 对称加密算法
 *
 * @author leepsmart
 */
public class JwtUtil {
    private static final String SECRET = "$#%#()*&&_asdashan1235_?;!@#pm&";
    private static final String TOKEN_PREFIX = "Bearer_";
    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_ID = "id";

    /**
     * 加密获取 json web token
     *
     * @param map  需要加密的数据, Map 集合
     * @param time 过期时间/秒
     * @return
     */
    public static String generateToken(Map<String, Object> map, int time) {

        // 优化token的生层规则
        String jwt = Jwts.builder()
                .setSubject(HEADER_ID).setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * time))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + jwt;
    }

    // 解密token 获取数据
    public static Map<String, Object> validateToken(String token) throws ExpiredJwtException {
        Map<String, Object> tokenBody = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        return tokenBody;
    }

    /**
     * 移到jwtUtil中去
     *
     * @param token
     * @return
     */
    public static Map<String, String> validateTokenAndUser(String token) {
        Map<String, String> tokenResultMap = new HashMap<>();
        if (StringUtils.isEmpty(token)) {
            return tokenResultMap;
        }
        return tokenResultMap;
    }

}
