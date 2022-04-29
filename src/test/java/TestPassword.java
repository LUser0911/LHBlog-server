import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/23 9:48
 * @description
 */
public class TestPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("11.lh2blog*09");
        System.out.println(encode);
        //$2a$10$ua8KFzZSxLJSPw4NxX0E6.MIsV49kaocUKHyt/9k07PJE2w0uKVhS
        //$2a$10$pg1mwBq55Rp/a38YpNq1beUCt7merTKTbdAKp52WWSs4sFlnWBAw2
    }
}
