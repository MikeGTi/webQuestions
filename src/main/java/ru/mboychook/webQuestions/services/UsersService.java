package ru.mboychook.webQuestions.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.webQuestions.models.Role;
import ru.mboychook.webQuestions.models.RoleEnum;
import ru.mboychook.webQuestions.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mboychook.webQuestions.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
//@Secured("ROLE_ADMIN")
@Transactional(readOnly = true)
public class UsersService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        if (userFromDB.isEmpty()){ throw new UsernameNotFoundException("User with this username not found"); }
        return userFromDB.get();
    }

    public UserDetails loadUserByEmail(String userEmail) throws UsernameNotFoundException {
        Optional<User> userFromDB = userRepository.findByEmail(userEmail);
        if (userFromDB.isEmpty()) { throw new UsernameNotFoundException("User with this e-mail not found"); }
        return userFromDB.get();
    }

    public User findOne(UUID id) { return userRepository.findById(id).orElse(new User());    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findUsersByPartName(String usernamePart){
        return userRepository.findByUsernameContaining(usernamePart);
    }

    /*@Transactional
    public boolean create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }*/

    @Transactional
    public boolean save(User user) {
        /*Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isEmpty()) { return false; }*/

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void update(UUID id, User updatedUser) {
        updatedUser.setId(id);

        /*//filter only updated/new roles
        Optional<User> userFromDB = userRepository.findByUsername(updatedUser.getUsername());
        if (userFromDB.isPresent()) {
            List<Role> userFromDBrolesSet = userFromDB.get().getRoles();
            List<Role> updatedUserRolesSet = updatedUser.getRoles();
            List<Role> filteredRolesSet =  new HashSet<>();

            userFromDBrolesSet.forEach(role1 -> {
                updatedUserRolesSet.stream()
                                   .filter(role2 -> !role1.equals(role2))
                                   .map(role2 -> role1)
                                   .forEach(filteredRolesSet::add);
            });
            updatedUser.setRoles(filteredRolesSet);

        }*/

        /*if (updatedUser.getRoles().size()==0) {
            updatedUser.getRoles().add(new Role(3L, "GUEST"));
        }*/
        userRepository.save(updatedUser);
    }

    @Transactional
    public boolean delete(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
