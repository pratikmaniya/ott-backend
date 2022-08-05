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
        if(user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null){
            throw new Exception("Please fill all the details");
        } else if(!(user.getFirstName() instanceof String) || !(user.getLastName() instanceof String) || !(user.getEmail() instanceof String) || !(user.getPassword() instanceof String)){
            throw new Exception("Invalid data types, Please check data types");
        } else {
            Optional<UserModel> dbUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
            if(dbUser.isPresent()){
                throw new Exception("User with email: " + user.getEmail() + " is already exists. Please choose the different email");
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
        user.get().setPassword(null);
        return user;
    }

    public UserModel getUserByEmail(String email) throws Exception{
        UserModel user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        UserModel foundUser = userRepository.findByEmail(email);

        String femail = foundUser.getEmail();
        String password = foundUser.getPassword();

        return new User(femail, password, new ArrayList<>());
    }

}
