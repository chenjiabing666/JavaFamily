import org.springframework.util.AntPathMatcher;

public class Test {
    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        System.out.println(matcher.match("/user/**","/user/dd/aa"));
    }
}
