import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @atuthor JackLove
 * @date 2019-10-13 9:37
 * @Package PACKAGE_NAME
 */
public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();
        System.out.println(en.matches("admin", "$2a$10$F6iZW.1mqIigibCvNkl3j.aumv.5HqjXJaP2YrDQBtGX7Cf6fslYG"));
    }
}
