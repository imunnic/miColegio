package es.mde.miColegio.seguridad.config;

import es.mde.miColegio.seguridad.ExceptionHandler_JwtAuthenticationFilter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
  private final JwtAuthenticationFilter FILTER;
  private final AuthenticationProvider PROVIDER;

  public ConfiguracionSeguridad(JwtAuthenticationFilter FILTER, AuthenticationProvider PROVIDER) {
    this.FILTER = FILTER;
    this.PROVIDER = PROVIDER;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(
                  authRequest -> authRequest
                                 .requestMatchers("/api/auth/**")
                                 .permitAll()
                                 .anyRequest()
                                 .authenticated())
               .sessionManagement(sessionManager ->
                 sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(this.PROVIDER)
               .addFilterBefore(this.FILTER, UsernamePasswordAuthenticationFilter.class)
               .build();
  }
}
