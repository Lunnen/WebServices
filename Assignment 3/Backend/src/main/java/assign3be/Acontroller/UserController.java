package assign3be.Acontroller;

import assign3be.Bservice.UserService;
import assign3be.datahandlers.DTO.AuthDTO;
import assign3be.datahandlers.UserCreate;
import assign3be.Crepository.entity.UserEntity;
import assign3be.Crepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String registerUser(
            @RequestBody UserCreate user, HttpServletResponse response) {
        int code = userService.registerUser(user);
        switch (code) {
            case 1:
                response.setStatus(409);
                return "There is already a user with that username";
            case 0:
                return "User has been registered.";
            default:
                response.setStatus(500);
                return "Something went wrong.";
        }
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestHeader("username") String username, @RequestHeader("password") String password, HttpServletResponse response) {
        String token = userService.login(username, password);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Check your input!");
        }

        return new AuthDTO(username, token);
    }


    @PostMapping("/logout")
    public String login(@RequestHeader("token") String token, HttpServletResponse response) {
        int code = userService.logout(token);

        switch (code) {
            case 1:
                response.setStatus(404);
                return "No such user!";
            case 0:
                return "You've been logged out!";
            default:
                response.setStatus(500);
                return "Something went wrong.";
        }
    }

    // --------------------------------------
    // TODO - TEST AREA - remove when done <---
    @Autowired
    UserRepository userRepository;

    @GetMapping(path="/all2")
    public @ResponseBody Iterable<UserEntity> getAllUsers2() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }


}
