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
                .excludePathPatterns("/places/dev")
                .excludePathPatterns("/re-issue");
    }
}
