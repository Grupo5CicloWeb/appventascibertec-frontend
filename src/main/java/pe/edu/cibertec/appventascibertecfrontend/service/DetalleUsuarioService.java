package pe.edu.cibertec.appventascibertecfrontend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.appventascibertecfrontend.model.bd.Rol;
import pe.edu.cibertec.appventascibertecfrontend.model.bd.Usuario;
import pe.edu.cibertec.appventascibertecfrontend.model.security.UsuarioSecurity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {

    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findUserByUserName(username);
        return autenticacionUsuario(
                usuario, obtenerListaRolesUsuario(usuario.getRoles())
        );
    }

    private List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> listRoles){
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rol rol : listRoles) {
            roles.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UsuarioSecurity autenticacionUsuario(
            Usuario usuario, List<GrantedAuthority> authorityList){
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity(usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true,
                true,
                true, authorityList);
        usuarioSecurity.setEmail(usuario.getEmail());
        return usuarioSecurity;
    }
}
