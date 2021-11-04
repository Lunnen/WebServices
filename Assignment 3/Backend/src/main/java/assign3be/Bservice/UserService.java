package assign3be.Bservice;

import assign3be.datahandlers.UserCreate;
import assign3be.Crepository.entity.UserEntity;
import assign3be.Crepository.UserRepository;
import assign3be.shared.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public int registerUser(UserCreate inputData) {
        UserEntity existing = userRepository.getUserEntityByUsernameEquals(inputData.getUsername());
        if (existing != null)
            return 1;

        UserEntity newUser = new UserEntity();
        newUser.setUsername(inputData.getUsername());

        if(Util.encrypt(inputData.getPassword()) == null ){
            return -1; //couldn't hash pw
        }
        newUser.setPassword(Util.encrypt(inputData.getPassword()));
        userRepository.save(newUser);
        return 0;
    }

    public String login(String username, String password) {
        UserEntity searchedUser = userRepository.getUserEntityByUsernameEquals(username);
        if(searchedUser == null) return null;

        String decryptedPW = Util.decrypt(searchedUser.getPassword());

        if (decryptedPW == null || !decryptedPW.equals(password)){
            return null;
        }

        String token = UUID.randomUUID().toString();
        searchedUser.setToken(token);
        userRepository.save(searchedUser);

        return token;
    }

    public int logout(String token) {
        UserEntity searchedUser = userRepository.getUserEntityByTokenEquals(token);
        if(searchedUser == null) return 1;

        searchedUser.setToken(null);
        userRepository.save(searchedUser);

        return 0;
    }


    public UserEntity validate(String token) {
        return userRepository.getUserEntityByTokenEquals(token);
    }


}
