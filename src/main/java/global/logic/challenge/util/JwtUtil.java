package global.logic.challenge.util;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    /*TODO: The SECRET will be in a security plase like a keys in a Cloud provider or a config file*/
    private static final String SECRET = "my-32-character-ultra-secure-and-ultra-long-secret";
    private static final long EXPIRATION_TIME = 86_4000_00; // 1 day
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
