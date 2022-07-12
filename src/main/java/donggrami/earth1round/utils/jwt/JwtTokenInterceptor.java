/*
package donggrami.earth1round.utils.jwt;

import donggrami.earth1round.config.BaseException;
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
            response.setStatus(401);
            response.setHeader("message", "Check the token.");
            //throw new BaseException(EMPTY_JWT);
            return false;
        }
        jwtService.isValidAccessToken(accessToken);
        Long userIdByJwt = jwtService.getUserId(accessToken);
        request.setAttribute("userIdByJwt", userIdByJwt);

        return true;
    }
}
*/
