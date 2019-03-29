package darkversum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange().anyExchange().permitAll().and().build();
	}
//		return http.authorizeExchange()
//				.pathMatchers("/player", "/match").hasRole("USER")
//				.anyExchange().authenticated()
//				.and()
//				.httpBasic().and()
//				.build();
//	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		PasswordEncoder encoder =
				PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user =
				User.withUsername("user")
						.passwordEncoder(encoder::encode)
						.password("dupa")
						.roles("USER")
						.build();

		return new MapReactiveUserDetailsService(user);
	}
}
