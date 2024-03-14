package com.example.courses.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.courses.entity.ERole;
import com.example.courses.entity.Role;
import com.example.courses.entity.User;
import com.example.courses.exception.ResourceNotFoundException;
import com.example.courses.payload.request.LoginRequest;
import com.example.courses.payload.request.SignupRequest;
import com.example.courses.payload.response.JwtResponse;
import com.example.courses.payload.response.MessageResponse;
import com.example.courses.repository.RoleRepository;
import com.example.courses.repository.UserRepository;
import com.example.courses.security.UserDetailsImpl;
import com.example.courses.security.jwt.AuthTokenFilter;
import com.example.courses.security.jwt.JwtUtils;
import com.example.courses.security.jwt.TokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
//
    private final AuthTokenFilter authTokenFilter;

    private final TokenBlacklist tokenBlacklist;

    public AuthController(AuthTokenFilter authTokenFilter, TokenBlacklist tokenBlacklist) {
        this.authTokenFilter = authTokenFilter;
        this.tokenBlacklist = tokenBlacklist;
    }

    //    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        User savedUser = userRepository.saveuser(user.getEmail(), user.getPassword(), user.getUsername())
                .orElseThrow();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            String urole = ERole.ROLE_USER.toString();
//
            userRepository.addRoleToUser(savedUser.getId(), 1L);
        } else {

            strRoles.forEach(role -> {

                if (role.equals("admin")) {
//                    String arole = ERole.ROLE_ADMIN.toString();
//
                    userRepository.addRoleToUser(savedUser.getId(), 2L);

                } else if (role.equals("user")) {
//                    String urole = ERole.ROLE_USER.toString();
//
                    userRepository.addRoleToUser(savedUser.getId(), 1L);
                } else {
                    throw new RuntimeException("strange role!");
                }

            });
        }

//        user.setRoles(roles);

//        user.setRoles(null);


//        userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }


    @PutMapping("addRule/{id}")
    public ResponseEntity<?> addRule(@RequestBody Role role, @PathVariable(name = "id") Long userId) {

        if (!userRepository.findUser(userId).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found!"));
        }
        if (!roleRepository.findById(Long.valueOf(role.getId())).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role not found!"));
        }
        if (userRepository.findUserRoles(userId, Long.valueOf(role.getId())).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: user has this role"));
        }

        User user = userRepository.addRoleToUser(userId, Long.valueOf(role.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Error !"));
        ;

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("deleteRule/{id}")
    public ResponseEntity<?> deleteRule(@RequestBody Role role, @PathVariable(name = "id") Long userId) {

        if (!userRepository.findUser(userId).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found!"));
        }
        if (!roleRepository.findById(Long.valueOf(role.getId())).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role not found!"));
        }
        if (!userRepository.findUserRoles(userId, Long.valueOf(role.getId())).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: user not have this role"));
        }

        User user = userRepository.deleteRoleFromUser(userId, Long.valueOf(role.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Error !"));
        ;

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = authTokenFilter.parseJwt(request);
        tokenBlacklist.addToBlacklist(token);

        // Clear any session-related data if necessary

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userRepository.findUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " Not found!"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
