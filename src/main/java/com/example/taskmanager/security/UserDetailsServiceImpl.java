package com.example.taskmanager.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;
    public UserDetailsServiceImpl(UserRepository userRepo){ this.userRepo = userRepo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
            .password(u.getPassword())
            .authorities(u.getRoles().stream().map(r-> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()))
            .build();
    }
}
