package donggrami.earth1round.utils.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import donggrami.earth1round.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static donggrami.earth1round.config.BaseResponseStatus.EMPTY_JWT;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private final JwtService jwtService;

    public JwtTokenInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String accessToken = request.getHeader("ACCESS-TOKEN");

        if (accessToken == null || accessToken.length() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            BaseResponse<Object> failed = new BaseResponse<>(EMPTY_JWT);
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(mapper.writeValueAsString(failed));

            return false;
        }
        jwtService.isValidAccessToken(accessToken);

        return true;
    }
}
