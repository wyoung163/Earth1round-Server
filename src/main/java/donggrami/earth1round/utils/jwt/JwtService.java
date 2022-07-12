package donggrami.earth1round.utils.jwt;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.secret.Secret;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static donggrami.earth1round.config.BaseResponseStatus.EXPIRED_JWT;
import static donggrami.earth1round.config.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    /**
     * JWT 생성 - Access Token
     * @param user_id
     * @return String
     */
    public String createAccessToken(Long user_id) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("user_id", user_id)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 3*(1000*60*60*24)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * JWT 생성 - Refresh Token
     * @param user_id
     * @return String
     */
    public String createRefreshToken(Long user_id) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("user_id", user_id)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 20*(1000*60*60*24)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Access token 유효성 확인
     * @param jwt
     * @return boolean
     */
    protected boolean isValidAccessToken(String jwt) throws BaseException {
        try {
            Claims claims = getClaims(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_JWT);
        } catch (JwtException e) {
            throw new BaseException(INVALID_JWT);
        }
    }

    /**
     * Refresh token 유효성 확인
     * @param refreshToken
     * @return boolean
    */
    public boolean isValidRefreshToken(String refreshToken) throws BaseException {
        try {
            Claims claims = getClaims(refreshToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_JWT);
        } catch (JwtException e) {
            throw new BaseException(INVALID_JWT);
        }
    }

    /**
     * Header에서 ACCESS-TOKEN 으로 JWT 추출
     * @return String
    */
    private String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getHeader("ACCESS-TOKEN");
    }

    /**
     * JWT에서 user_id 추출
     * @return Long
     */
    public Long getUserId() throws BaseException {
        String accessToken = getAccessToken();
        this.isValidAccessToken(accessToken);

        Claims claims = getClaims(accessToken);

        return claims.get("user_id", Long.class);
    }

    public Long getUserIdWithRefreshToken(String refreshToken) {
        Claims claims = getClaims(refreshToken);

        return claims.get("user_id", Long.class);
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
