package ru.mboychook.webQuestions.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mboychook.webQuestions.models.User;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String userEmail);

    Optional<User> findById(UUID userId);

    List<User> findAllById(UUID userId);

    void deleteById(UUID userId);


    List<User> findByUsernameContaining(String usernamePart);
}
