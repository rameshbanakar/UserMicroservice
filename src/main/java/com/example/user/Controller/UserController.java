package com.example.user.Controller;

import com.example.user.Dto.LoginDto;
import com.example.user.Dto.SingupDto;
import com.example.user.Dto.UserResponseDto;
import com.example.user.Exceptions.EmailAlreadyExistsException;
import com.example.user.Exceptions.InvalidCredentialsException;
import com.example.user.Exceptions.UserNotFound;
import com.example.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginDto loginDto){
        try{
            UserResponseDto userResponseDto=userService.userLogin(loginDto.getEmail(),loginDto.getPassword());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseDto);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message",e.getMessage()));
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody SingupDto singupDto){
        try{
            System.out.println(singupDto);
            UserResponseDto userResponseDto=userService.userSingup(singupDto.getEmail(),singupDto.getPassword(),singupDto.getName(),singupDto.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
        catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message",e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
    }
}
