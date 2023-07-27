package com.springboot.blog.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.HashSet;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private User user;

    private Role role;
    @BeforeEach
    public void setupAll() {
        role = Role.builder()
                .name("TEST_ADMIN_ROLE")
                .build();

        roleRepository.save(role);

        user = User.builder()
                .email("test@gmail.com")
                .password("123")
                .username("test")
                .roles(Collections.singleton(role))
                .build();
        User user2 = User.builder()
                .email("test2@gmail.com")
                .password("123")
                .username("test2")
                .roles(Collections.singleton(role))
                .build();
        userRepository.saveAll(Arrays.asList(user, user2));
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    @DisplayName("Test for save user operation")
    @Test
    public void givenUser_whenSave_thenUserShouldBePersisted() {
        // given - precondition or setup
                User user = User.builder()
                        .email("testSavedUserOperation@gmail.com")
                        .password("123")
                        .username("testSavedUserOperation")
                        .build();

        // when - action or behaviour that we are going to test
        User savedUser = userRepository.save(user);

        //then - verify the outcomes
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @DisplayName("Test for get all users operation")
    @Test
    public void givenTwoUsers_whenFindAll_thenShouldReturnUsersList() {
        //given - precondition or setup
//        User user1 = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .build();
//
//        User user2 = User.builder()
//                .email("test2@gmail.com")
//                .password("1234")
//                .username("test2")
//                .build();
//
//        userRepository.save(user1);
//        userRepository.save(user2);

        // when - action or behaviour that we are going to test
        List<User> users = userRepository.findAll();

        //then - verify the outcomes
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    @DisplayName("Test for get user by id operation")
    @Test
    public void givenUser_whenFindById_thenShouldReturnUser() {

        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .build();
//        userRepository.save(user);

        // when - action or behaviour that we are going to test
        User foundUser = userRepository.findById(user.getId()).get();

        //then - verify the outcomes
        assertThat(foundUser).isNotNull();
    }

    @DisplayName("Test for get user by email operation ")
    @Test
    public void givenUsernameOrEmail_whenFindByUsernameOrEmail_thenShouldReturnUser() {
        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .build();
//        userRepository.save(user);

        // when - action or behaviour that we are going to test
        User foundUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).get();

        //then - verify the outcomes
        assertThat(foundUser).isNotNull();
    }

    @DisplayName("Test for update user operation")
    @Test
    public void givenUser_whenUpdateUser_thenShouldReturnUpdatedUser() {
       // given - precondition or setup
        User testUser = User.builder()
                .email("testSaved@gmail.com")
                .password("123")
                .username("testSaved")
                .build();

        userRepository.save(testUser);

         // when - action or behaviour that we are going to test
        User foundUser = userRepository.findById(testUser.getId()).get();
        testUser.setUsername("Updated username");

        User updatedUser = userRepository.save(foundUser);

        //then - verify the outcomes
        assertThat(updatedUser.getUsername()).isEqualTo(foundUser.getUsername());
    }

    @DisplayName("Test for delete user operation")
    @Test
    public void givenUser_whenDelete_thenShouldDeleteUser() {
        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .build();
//
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        userRepository.deleteById(user.getId());
        Optional<User> userOptional = userRepository.findById(user.getId());
        //then - verify the outcomes
        assertThat(userOptional).isEmpty();
    }

    @DisplayName("Test for custom query using JPQL")
    @Test
    public void givenIdOrUsername_whenFindByIdOrUsername_thenShouldReturnUser() {
        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .build();
//
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        var foundUser = userRepository.findByIdAndUsername(user.getId(), user.getUsername());
        //then - verify the outcomes
        assertThat(foundUser).isNotEmpty();
    }

    @DisplayName("Test to retrieve user roles by user id")
    @Test
    public void givenUserWithRoles_whenFindRolesByUserId_thenShouldReturnUserRoles() {
//        Role role = Role.builder()
//                .name("TEST_ROLE")
//                .build();
//        roleRepository.save(role);
//
//        given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .roles(Collections.singleton(role))
//                .build();
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        Optional<List<Role>> userRoles = userRepository.findRolesByUserId(user.getId());
        //then - verify the outcomes
        assertThat(userRoles).isNotEmpty();
        assertThat(userRoles.get().get(0).getName()).isEqualTo(role.getName());
    }

    @DisplayName("Test to retrieve user roles by user id with named param")
    @Test
    public void givenUserWithRoles_whenFindRolesByUserIdNamedParam_thenShouldReturnUserRoles() {
//        Role role = Role.builder()
//                .name("TEST_ROLE")
//                .build();
//        roleRepository.save(role);
//
//        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .roles(Collections.singleton(role))
//                .build();
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        Optional<List<Role>> userRoles = userRepository.findRolesByUserIdNamedParam(user.getId());
        //then - verify the outcomes
        assertThat(userRoles).isNotEmpty();
        assertThat(userRoles.get().get(0).getName()).isEqualTo(role.getName());
    }

    @DisplayName("Test for get user by native query")
    @Test
    public void givenUsernameOrEmail_whenFindUserByUsernameOrEmailNative_thenShouldReturnUserWithRoles() {
        //given - precondition or setup
//        Role role = Role.builder()
//                .name("TEST_ROLE")
//                .build();
//        roleRepository.save(role);
//
//        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .roles(Collections.singleton(role))
//                .build();
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        User foundUser = userRepository.findUserByUsernameOrEmailNative(user.getUsername(), user.getEmail());
        //then - verify the outcomes
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getRoles()).isNotNull();
    }

    @DisplayName("Test for get user by native query with named params")
    @Test
    public void givenUsernameOrEmail_whenFindUserByUsernameOrEmailNativeNamedParams_thenShouldReturnUserWithRoles() {
        //given - precondition or setup
//        Role role = Role.builder()
//                .name("TEST_ROLE")
//                .build();
//        roleRepository.save(role);
//
//        //given - precondition or setup
//        User user = User.builder()
//                .email("test@gmail.com")
//                .password("123")
//                .username("test")
//                .roles(Collections.singleton(role))
//                .build();
//        userRepository.save(user);
        // when - action or behaviour that we are going to test
        User foundUser = userRepository.findUserByUsernameOrEmailNativeNamedParams(user.getUsername(), user.getEmail());
        //then - verify the outcomes
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getRoles()).isNotNull();
    }

}


