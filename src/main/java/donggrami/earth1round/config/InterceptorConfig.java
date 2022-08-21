package donggrami.earth1round.config;

import donggrami.earth1round.utils.jwt.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final JwtTokenInterceptor jwtTokenInterceptor;

    public InterceptorConfig(JwtTokenInterceptor jwtTokenInterceptor) {
        this.jwtTokenInterceptor = jwtTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .excludePathPatterns("/login/kakao")
                .excludePathPatterns("/login/google")
                .excludePathPatterns("/re-issue")
                .excludePathPatterns("/places/dev");
                //.excludePathPatterns("/unlink") <-- 카카오 test할 때는 jwt를 불러올 수가 없어서 넣어서 test했습니다!
    }
}
