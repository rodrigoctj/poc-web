package br.com.aguiabranca.pocweb.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SsoWrapper {
	
	private String serverUrl;
	private String realm;
	private String clientId;
	private String clientSecret;
	private String grantType;
	private String username;
	private String password;
	
}
