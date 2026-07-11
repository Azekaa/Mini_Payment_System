package mini.payment.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mini.payment.domain.UserService;
import mini.payment.dto.UserDto;
import mini.payment.dto.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id){
        return userService.getUserOrThrow(id);
    }

}
