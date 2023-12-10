package global.logic.challenge.util;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "my32characterultrasecureandultra-ongsecret";
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

    public static boolean validateToken(String token, String username) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return username.equals(claimsJws.getBody().getSubject()) && !isTokenExpired(claimsJws.getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
