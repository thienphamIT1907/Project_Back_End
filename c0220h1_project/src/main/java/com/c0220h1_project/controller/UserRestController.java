
package com.c0220h1_project.controller;

import com.c0220h1_project.model.Exam;
import com.c0220h1_project.model.User;
import com.c0220h1_project.model.UserPrincipal;
import com.c0220h1_project.model.login_msg.request.Login;
import com.c0220h1_project.model.login_msg.response.JwtResponse;
import com.c0220h1_project.payload.UpdatePasswordToken;
import com.c0220h1_project.payload.response.ApiResponse;
import com.c0220h1_project.security.JwtProvider;
import com.c0220h1_project.service.exam.ExamService;
import com.c0220h1_project.service.test.TestService;
import com.c0220h1_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping(path = "/users")
public class UserRestController {
    @Autowired
    UserService userService;

    /////////// THIEN ///////////
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    TestService testService;

    @Autowired
    ExamService examService;

    private final String NOT_FOUND_USER = "Cannot find this user!";

    private final String NOT_FOUND_EXAMS = "Cannot find exam list!";

    private PasswordEncoder encoder;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/listUser")
    public ResponseEntity<Page<User>> getListUser(@PageableDefault(size = 10) Pageable pageable) {
        if (userService.findAll(pageable).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

//    @GetMapping("/register")
//    public ResponseEntity registerUser(User user){
//        if (userService.save(user)){
//            return new ResponseEntity<>(null,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null,HttpStatus.CONFLICT);
//    }

    @GetMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
            userService.deleteUser(id);
            return new ResponseEntity(null,HttpStatus.OK);
    }
    @GetMapping("/new-user")
    public ResponseEntity<User> findUserNew(){
        return new ResponseEntity<>(userService.findTopByOrderByIdDesc(), HttpStatus.OK);
    }

    /////////// THIEN ///////////
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login loginRequest) throws AuthenticationException {

        Authentication authentication = authManager.authenticate (
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generatingJwt(authentication);
        System.out.println("Token is generated: " + token);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        System.out.println("UserDetails: " + userPrincipal.getUsername());

        JwtResponse response = new JwtResponse(
                token,
                userPrincipal.getUsername(),
                userPrincipal.getAuthorities()
        );
        return ResponseEntity.ok(response);
    }

    //    tinh - get user by id

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findUserById(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    tinh - update user

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User currentUser = userService.findById(id);
        if (currentUser == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        currentUser.setUsername(user.getUsername());
        currentUser.setFullName(user.getFullName());
        currentUser.setEmail(user.getEmail());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        userService.save(currentUser);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(currentUser.getId()).toUri();
//        return ResponseEntity.created(location).body(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

//    tinh - update password

    @PatchMapping("/changePassword/{id}")
    public ResponseEntity<Object> changePassword(@PathVariable Integer id, @RequestBody UpdatePasswordToken updatePasswordToken) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        if (!encoder.matches(updatePasswordToken.getCurrentPassword(), user.getUser_password())) {
            return new ResponseEntity<>(new ApiResponse(false, "The password is incorrect!"), HttpStatus.BAD_REQUEST);
        }
        user.setUser_password(encoder.encode(updatePasswordToken.getNewPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    tinh - test history

    @GetMapping(value = "/history/{id}")
    public ResponseEntity<Object> getTestHistory(@PathVariable("id") Integer id) {
        List<Exam> exams = examService.findByUserId(id);
//        double sum = 0;
//        double avg;
//        int count = 0;
        if (exams == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_EXAMS), HttpStatus.NOT_FOUND);
        }
//        else {
//            for (Exam exam : exams) {
//                 exam.getTest().getTestName();
//                 exam.getMark();
//                 exam.getExamDate();
//                sum = sum + exam.getMark();
//                count += 1;
//            }
//            avg = sum / count;
//        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }


}

