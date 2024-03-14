package com.example.courses.repository;

import com.example.courses.entity.ERole;
import com.example.courses.entity.Role;
import com.example.courses.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Override
//    @Modifying
//    @Transactional
//    @Query(value = "  insert into [user] (email,password,username) values (?,?,?)" , nativeQuery = true)
//    <S extends Role> S save(S entity);

//    @Modifying
//    @Transactional
//    @Query(value = "  insert into [user] (email,password,username) values (?,?,?)" , nativeQuery = true)
//    Optional<Role> saveuser (User user);

    @Modifying
    @Transactional
    @Query(value = " select * from [role]  where [role].[name] =?1 " , nativeQuery = true)
    Optional<Role> findByName(String name);
}
