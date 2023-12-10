package global.logic.challenge.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class PasswordUtil {
    private static final int strength = 10;
    public static String passwordEncoder(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}
