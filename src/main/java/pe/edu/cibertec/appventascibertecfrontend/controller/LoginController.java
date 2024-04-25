package pe.edu.cibertec.appventascibertecfrontend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.cibertec.appventascibertecfrontend.model.bd.Usuario;
import pe.edu.cibertec.appventascibertecfrontend.model.security.UsuarioSecurity;
import pe.edu.cibertec.appventascibertecfrontend.service.UsuarioService;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class LoginController {

    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        return "auth/frmLogin";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "auth/frmRegistroUsuario";
    }

    @GetMapping("/login-success")
    public String loginSucces(){
        return "redirect:/auth/dashboard";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario){
        usuarioService.saveUser(usuario);
        return "auth/frmLogin";
    }



    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // Obtén el objeto UserDetails del contexto de seguridad
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Convierte el UserDetails a tu clase Usuario
        UsuarioSecurity usuario = (UsuarioSecurity) userDetails;
        // Ahora puedes acceder a los atributos específicos de tu clase Usuario
        String email = usuario.getEmail();
        // Realiza cualquier otra operación que necesites con el objeto Usuario
        session.setAttribute("usuario", email);
        return "auth/home"; // Return the dashboard page
    }

    /*@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidate the HttpSession to log the user out
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/login";
    }*/
}
