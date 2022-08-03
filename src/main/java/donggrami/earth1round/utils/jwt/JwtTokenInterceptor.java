package donggrami.earth1round.utils.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.config.BaseResponseStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static donggrami.earth1round.config.BaseResponseStatus.EMPTY_JWT;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final AuthorizationExtractor authorizationExtractor;

    public JwtTokenInterceptor(JwtService jwtService, AuthorizationExtractor authorizationExtractor) {
        this.jwtService = jwtService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try{
            String accessToken = authorizationExtractor.extract(request, "Bearer ");

            if (accessToken == null || accessToken.length() == 0) {
                exceptionHandler(response, EMPTY_JWT);
                return false;
            }
            jwtService.isValidAccessToken(accessToken);
            return true;
        } catch (BaseException e) {
            exceptionHandler(response, e.getStatus());
            return false;
        }
    }

    private void exceptionHandler(HttpServletResponse response, BaseResponseStatus status) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BaseResponse<Object> failed = new BaseResponse<>(status);
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(mapper.writeValueAsString(failed));
    }
}
