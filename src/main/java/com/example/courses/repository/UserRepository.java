package com.example.courses.repository;

import com.example.courses.entity.Role;
import com.example.courses.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = " select * from [user]  where [user].[username] =?1 ", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = " select * from [user]  where [user].[username] =?1 ", nativeQuery = true)
    Optional<User> existsByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = " select * from [user]  where [user].[email] =?1 ", nativeQuery = true)
    Optional<User> existsByEmail(String email);


    @Modifying
    @Transactional
    @Query(
            value = "  insert into [user] (email,password,username) values (:value1,:value2,:value3) ;" +
                    " select * from [user] where (username) =:value3 ;"
            , nativeQuery = true)
    Optional<User> saveuser(@Param("value1") String email,
                            @Param("value2") String password,
                            @Param("value3") String username
    );

    @Modifying
    @Transactional
    @Query(value = " select * from [user] u join [user_roles] r on (u.id = r.user_id) " +
            "where u.id =:uId and role_id =:rId", nativeQuery = true)
    Optional<User> findUserRoles(@Param("uId") Long userId, @Param("rId") Long roleId);

    @Modifying
    @Transactional
    @Query(value = " select * from [user] u  where u.id =:uId ", nativeQuery = true)
    Optional<User> findUser(@Param("uId") Long id);


    @Modifying
    @Transactional
    @Query(value = "insert into [user_roles] (role_id , user_id) values (:rId,:uId) ;" +
            " select * from [user] u  where u.id =:uId  ", nativeQuery = true)
    Optional<User> addRoleToUser(@Param("uId") Long userId, @Param("rId") Long roleId);

    @Modifying
    @Transactional
    @Query(value = "delete from [user_roles]  where role_id =:rId and user_id =:uId ;" +
            " select * from [user] u  where u.id =:uId  ", nativeQuery = true)
    Optional<User> deleteRoleFromUser(@Param("uId") Long userId, @Param("rId") Long roleId);


//    @Override
//    @Modifying
//    @Transactional
//    @Query(value = "  insert into [user] (email,password,username) values (?,?,?)" ,nativeQuery = true )
//    <S extends User> S save(S entity);
}
