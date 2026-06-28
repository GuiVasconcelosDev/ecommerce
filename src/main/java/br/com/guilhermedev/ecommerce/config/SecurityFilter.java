package br.com.guilhermedev.ecommerce.config;

import br.com.guilhermedev.ecommerce.model.Cliente;
import br.com.guilhermedev.ecommerce.repository.ClienteRepository;
import br.com.guilhermedev.ecommerce.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;

    public SecurityFilter(TokenService tokenService, ClienteRepository clienteRepository) {
        this.tokenService = tokenService;
        this.clienteRepository = clienteRepository;
    }
    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = recuperarToken(request);

        if (token != null) {
            var email = tokenService.validarToken(token);

            if (!email.isEmpty()) {
                Cliente cliente = clienteRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(cliente, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
