package mx.edu.utez.springboot8b.services;

import mx.edu.utez.springboot8b.entity.UserInfo;
import mx.edu.utez.springboot8b.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userInfo = repository.findByUsername(username);

        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"))
    }

    public String saveUser(UserInfo userInfo){
        userInfo.setNomloked(true);
        userInfo.setPassword(
                passwordEncoder.encode(
                        userInfo.getPassword()
                )
        );
        repository.save(userInfo);
        return "Usuario guardado correctamente";
    }
}
