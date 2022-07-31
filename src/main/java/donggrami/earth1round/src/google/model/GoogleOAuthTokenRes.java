package donggrami.earth1round.src.google.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GoogleOAuthTokenRes {

    private String accessToken;
    private String expiresIn;
    private String scope;
    private String tokenType;
    private String idToken;

//    public OAuthToken(String accessToken, String expiresIn, String idToken, String scope, String tokenType) {
//        this.accessToken = accessToken;
//        this.expiresIn = expiresIn;
//        this.idToken = idToken;
//        this.scope = scope;
//        this.tokenType = tokenType;
//    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}