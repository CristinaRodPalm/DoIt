package com.doitteam.doit.repository;

import com.doitteam.doit.domain.UserExt;
import com.doitteam.doit.domain.Amistad;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExt entity.
 */
@SuppressWarnings("unused")
public interface UserExtRepository extends JpaRepository<UserExt,Long> {

    @Query("select userExt from UserExt userExt where userExt.user.id =:idUser")
    UserExt findByUserID(@Param("idUser") Long idUser);

}
