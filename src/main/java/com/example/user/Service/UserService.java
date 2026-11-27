package com.example.user.Service;

import com.example.user.Dto.UserResponseDto;
import com.example.user.Exceptions.EmailAlreadyExistsException;
import com.example.user.Exceptions.InvalidCredentialsException;
import com.example.user.Exceptions.UserNotFound;
import com.example.user.Model.Role;
import com.example.user.Model.User;
import com.example.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;


    public UserResponseDto userLogin(String email,String password) throws InvalidAttributesException {
        if(email==null || password==null){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

        Optional<User> userOptional=userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFound("User not found");
        }
        User user=userOptional.get();
        boolean passwordMatch=passwordEncoder.matches(password,user.getPassword());

        if(!passwordMatch){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }
        String token=jwtService.generateToken(user);
        UserResponseDto dto=new UserResponseDto();
        dto.setToken(token);

        return dto;
    }

    public UserResponseDto userSingup(String email,String password,String name,String role) {
        if (email == null || password == null || password == null) {
            throw new InvalidCredentialsException("Invalid argument");
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role.toUpperCase()));

        String token=jwtService.generateToken(user);

        UserResponseDto dto=new UserResponseDto();
        dto.setToken(token);
        userRepository.save(user);
        return dto;
    }


}
