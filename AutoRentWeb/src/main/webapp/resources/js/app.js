/**
 * AutoRent - Interatividade Front-End & Utilitários de Interface
 */

function toggleSidebar() {
    document.body.classList.toggle('sidebar-open');
}

function closeSidebar() {
    document.body.classList.remove('sidebar-open');
}

// Fechar sidebar com tecla Escape
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape' && document.body.classList.contains('sidebar-open')) {
        closeSidebar();
    }
});

// Alternador de Abas de Autenticação (Login / Cadastro)
function switchAuthTab(tab) {
    var loginPanel = document.getElementById('loginPanel');
    var signupPanel = document.getElementById('signupPanel');
    var tabLogin = document.getElementById('tabLogin');
    var tabSignup = document.getElementById('tabSignup');

    if (!loginPanel || !signupPanel || !tabLogin || !tabSignup) {
        return;
    }

    if (tab === 'login') {
        loginPanel.classList.add('active');
        signupPanel.classList.remove('active');
        tabLogin.classList.add('active');
        tabSignup.classList.remove('active');
    } else {
        signupPanel.classList.add('active');
        loginPanel.classList.remove('active');
        tabSignup.classList.add('active');
        tabLogin.classList.remove('active');
    }
}
