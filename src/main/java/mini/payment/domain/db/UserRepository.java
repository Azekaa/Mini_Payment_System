package mini.payment.domain.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByEmailIgnoreCase(String email);
}
