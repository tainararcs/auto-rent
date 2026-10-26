package trcs.autorent.filter;

import java.io.IOException;

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trcs.autorent.enums.UserType;
import trcs.autorent.managedbean.UserMB;

@WebFilter("*.xhtml")
public class AuthFilter implements Filter {

    @Inject
    private UserMB userMB; // Injeta o Bean de Sessão do Usuário

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // Se for página pública, permite acesso direto.
        if (isPublicPage(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se o usuário está logado através do UserMB
        boolean isLoggedIn = (userMB != null && userMB.getLoggedUser() != null);

        // Se não está logado, redireciona para a tela de login.
        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        // Controle de acesso restrito a administradores/funcionários
        if (isAdminOnlyPage(requestURI)) {
            boolean isAdmin = userMB.getLoggedUser().getType() == UserType.EMPLOYEE;
            if (!isAdmin) {
                res.sendRedirect(req.getContextPath() + "/accessdenied.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }
    
    private boolean isPublicPage(String requestURI) {
        return requestURI.endsWith("login.xhtml")
            || requestURI.endsWith("register.xhtml")
            || requestURI.endsWith("accessdenied.xhtml")
            || requestURI.contains("/resources/")
            || requestURI.contains("/jakarta.faces.resource/")
            || requestURI.contains("/javax.faces.resource/");
    }
    
    private boolean isAdminOnlyPage(String requestURI) {
        return requestURI.endsWith("manageagencies.xhtml")
            || requestURI.endsWith("fleetconfig.xhtml");
    }
}