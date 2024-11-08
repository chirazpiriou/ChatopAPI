package com.project.api.configuration;



import java.security.PrivateKey;


import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;


import jakarta.annotation.PostConstruct;


import java.util.Base64;
import java.nio.file.Files;
import java.security.KeyFactory;

@Configuration

public class SpringSecurityConfig {

	@Value("${jwt.public.key.path}")
    private String publicKeyPath;

    @Value("${jwt.private.key.path}")
    private String privateKeyPath;

    private RSAPublicKey publicKey;
    private PrivateKey privateKey;

	@Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() throws Exception {
        this.publicKey = loadPublicKey();
        this.privateKey = loadPrivateKey();
		
    }

	private RSAPublicKey loadPublicKey() throws Exception {
		Resource publicKeyResource = resourceLoader.getResource(publicKeyPath);
        String publicKeyPEM = new String(Files.readAllBytes(publicKeyResource.getFile().toPath()));
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                                    .replace("-----END PUBLIC KEY-----", "")
                                    .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)keyFactory.generatePublic(spec);
    }

    private PrivateKey loadPrivateKey() throws Exception {
		try {
		Resource privateKeyResource = resourceLoader.getResource(privateKeyPath);
        String privateKeyPEM = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()));
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                                     .replace("-----END PRIVATE KEY-----", "")
                                     .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(spec);
        return key;
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("An unexpected error occurred.", e);

		}

    }
	

	


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		return http
				.csrf(csrf -> csrf.disable()) 
				.cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
		                        new OrRequestMatcher(
		                                new AntPathRequestMatcher("/api/auth/register"),
		                                new AntPathRequestMatcher("/api/auth/login")),
										new AntPathRequestMatcher("/**/*.png"),
										new AntPathRequestMatcher("/**/*.jpg"),
										new AntPathRequestMatcher("/**/*.jpeg"),
										new AntPathRequestMatcher("/**/*.gif"),
										new AntPathRequestMatcher("/images/**"),
										new AntPathRequestMatcher("/swagger-ui/**"),
                                		new AntPathRequestMatcher("/v3/api-docs/**")
		                ).permitAll()
						.anyRequest().authenticated()) 
				.httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.build();		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

	

	@Bean
	public JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
		return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}


	


}
