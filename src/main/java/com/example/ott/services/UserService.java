package com.example.ott.services;

import com.example.ott.models.UserModel;
import com.example.ott.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel insertUser(UserModel user) throws Exception{
        if(user.getUsername() == null || user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null){
            throw new Exception("Please fill all the details");
        } else if(!(user.getFirstName() instanceof String) || !(user.getLastName() instanceof String) || !(user.getEmail() instanceof String) || !(user.getUsername() instanceof String) || !(user.getPassword() instanceof String)){
            throw new Exception("Invalid data types, Please check data types");
        } else {
            Optional<UserModel> dbUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
            if(dbUser.isPresent()){
                throw new Exception("User with username: " + user.getUsername() + " is already exists. Please choose the different username");
            }else {
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                UserModel insertedUser = userRepository.insert(user);
                return insertedUser;
            }
        }
    }

    public Optional<UserModel> getUser(String id) throws Exception{
        Optional<UserModel> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new Exception("User with " + id + " is not found");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserModel foundUser = userRepository.findByUsername(username);

        String uname = foundUser.getUsername();
        String password = foundUser.getPassword();

        return new User(uname, password, new ArrayList<>());
    }

}
