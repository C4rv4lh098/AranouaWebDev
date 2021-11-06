package edu.ifam.aranoua.security.service;

import edu.ifam.aranoua.domain.Cliente;
import edu.ifam.aranoua.enuns.PerfilEnum;
import edu.ifam.aranoua.repository.ClienteRepository;
import edu.ifam.aranoua.security.domain.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findClienteByCpf(username);

        if (cliente == null){
            throw new UsernameNotFoundException(username);
        }

        return new JwtUser(cliente.getId(), cliente.getCpf(), cliente.getSenha(), mapToGrantedAuthorities(cliente.getPerfil()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(perfil.toString()));
        return authorities;
    }
}
