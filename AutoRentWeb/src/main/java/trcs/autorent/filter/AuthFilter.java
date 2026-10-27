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

/**
 * Filtro de segurança para interceção de requisições web (*.xhtml)[cite: 48].
 * Controla o acesso autenticado e restrições baseadas em papéis de utilizador (RBAC)[cite: 48].
 */
@WebFilter("*.xhtml")
public class AuthFilter implements Filter {

    @Inject
    private UserMB userMB; // Injeta o Bean de Sessão do Usuário[cite: 48]

    /**
     * Executa a filtragem de segurança verificando sessões e permissões de acesso às páginas[cite: 48].
     * 
     * @param request O pedido HTTP recebido[cite: 48]
     * @param response A resposta HTTP enviada[cite: 48]
     * @param chain A cadeia de filtros de execução[cite: 48]
     * @throws IOException Em caso de erro de I/O[cite: 48]
     * @throws ServletException Em caso de erro no servlet[cite: 48]
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // Se for página pública, permite acesso direto[cite: 48].
        if (isPublicPage(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se o usuário está logado através do UserMB[cite: 48]
        boolean isLoggedIn = (userMB != null && userMB.getLoggedUser() != null);

        // Se não está logado, redireciona para a tela de login[cite: 48].
        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        // Controle de acesso restrito a administradores/funcionários[cite: 48]
        if (isAdminOnlyPage(requestURI)) {
            boolean isAdmin = userMB.getLoggedUser().getType() == UserType.EMPLOYEE;
            if (!isAdmin) {
                res.sendRedirect(req.getContextPath() + "/accessdenied.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }
    
    /**
     * Verifica se a página requisitada é de acesso público (sem autenticação prévia)[cite: 48].
     * 
     * @param requestURI O caminho URI da requisição[cite: 48]
     * @return true se for pública, false caso contrário[cite: 48]
     */
    private boolean isPublicPage(String requestURI) {
        return requestURI.endsWith("login.xhtml")
            || requestURI.endsWith("register.xhtml")
            || requestURI.endsWith("accessdenied.xhtml")
            || requestURI.contains("/resources/")
            || requestURI.contains("/jakarta.faces.resource/")
            || requestURI.contains("/javax.faces.resource/");
    }
    
    /**
     * Verifica se a página requisitada é de acesso restrito a administradores[cite: 48].
     * 
     * @param requestURI O caminho URI da requisição[cite: 48]
     * @return true se for restrita a admins, false caso contrário[cite: 48]
     */
    private boolean isAdminOnlyPage(String requestURI) {
        return requestURI.endsWith("manageagencies.xhtml")
            || requestURI.endsWith("fleetconfig.xhtml");
    }
}