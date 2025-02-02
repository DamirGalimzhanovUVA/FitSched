import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return "User already exists!";
        }

        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser.isEmpty()) {
            return "User not found!";
        }

        // Assuming password is matched after encryption
        User foundUser = existingUser.get();
        if (new BCryptPasswordEncoder().matches(user.getPassword(), foundUser.getPassword())) {
            return "Login successful!";
        }

        return "Invalid credentials!";
    }
}
