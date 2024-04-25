package es.mde.miColegio.seguridad.ExceptionHandler_JwtAuthenticationFilter;

import es.mde.miColegio.seguridad.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService JWTSERVICE;
  private final UserDetailsService USERDETAILSSERVICE;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetails) {
    this.JWTSERVICE = jwtService;
    this.USERDETAILSSERVICE = userDetails;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String token = getTokenFromRequest(request);
    final String username;

    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    username = this.JWTSERVICE.getUsernameFromToken(token);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.USERDETAILSSERVICE.loadUserByUsername(username);

      if (this.JWTSERVICE.isTokenValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String AUTHHEADER = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(AUTHHEADER) && AUTHHEADER.startsWith("Bearer ")) {
      return AUTHHEADER.substring(7);
    }
    return null;
  }
}
