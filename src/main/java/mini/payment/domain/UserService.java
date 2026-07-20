package mini.payment.domain;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import mini.payment.domain.db.UserEntity;
import mini.payment.domain.db.UserRepository;
import mini.payment.dto.UserDto;
import mini.payment.dto.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PaymentEntityMapper mapper;

    @Transactional
    public UserDto createUser(UserRequest request){
        boolean exist = userRepository.existsByEmailIgnoreCase(request.email());
        if (exist){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User already exist: " + request.email());
        }
        UserEntity user = new UserEntity(request.email());
        UserEntity saved = userRepository.save(user);
        return toResponse(saved);
    }
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(){
        return userRepository.findAllWithUser().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public  UserDto getUserOrThrow(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"not found: " + id));
        return toResponse(user);
    }

    private UserDto toResponse(UserEntity user){
        return new UserDto(
                user.getId(),
                user.getEmail(),
                mapper.mapListToDto(user.getPayments())
        );
    }

}
