package com.springboot.blog.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("select u from User u where u.id = ?1 and u.username = ?2")
    Optional<User> findByIdAndUsername(Long id, String username);
    @Query("SELECT u.roles FROM User u where u.id = ?1")
    Optional<List<Role>> findRolesByUserId(Long id);
    @Query("SELECT u.roles FROM User u where u.id = :id")
    Optional<List<Role>> findRolesByUserIdNamedParam(@Param("id") Long id);
    @Query(value = "SELECT u.* FROM users u WHERE u.username = ?1 OR u.email = ?1", nativeQuery = true)
    User findUserByUsernameOrEmailNative(String username, String email);


    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username OR u.email = :email", nativeQuery = true)
    User findUserByUsernameOrEmailNativeNamedParams(@Param("username") String username, @Param("email") String email);

}
