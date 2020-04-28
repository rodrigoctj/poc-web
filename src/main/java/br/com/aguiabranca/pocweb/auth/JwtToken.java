package br.com.aguiabranca.pocweb.auth;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

public class JwtToken {
	
	private Keycloak keycloak;
		
	public JwtToken(SsoWrapper sso) {
		
		keycloak = KeycloakBuilder.builder()
                .clientId(sso.getClientId())
                .clientSecret(sso.getClientSecret())
                .grantType(sso.getGrantType())
                .realm(sso.getRealm())
                .serverUrl(sso.getServerUrl())
                .username(sso.getUsername())
                .password(sso.getPassword())
                .build();		
	}
	
	 public Token getToken() {
	        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
	        return new Token(
	                accessToken.getToken(),
	                accessToken.getExpiresIn(),
	                accessToken.getRefreshExpiresIn(),
	                accessToken.getRefreshToken(),
	                accessToken.getTokenType(),
	                accessToken.getIdToken(),
	                accessToken.getNotBeforePolicy(),
	                accessToken.getSessionState(),
	                accessToken.getOtherClaims(),
	                accessToken.getScope());
	    }
	
}
