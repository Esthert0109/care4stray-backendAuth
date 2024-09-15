package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Adoption;
import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    List<Adoption> findByUserIdOrderByCreatedDateDesc(Integer userId);

    List<Adoption> findAllByOrderByCreatedDateDesc();

    Adoption findByUserIdAndAdoptionId(Integer userId, Long adoptionId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Adoption a WHERE a.user = :user AND a.adoptionStatus = 'APPLICATION_SUCCESS'")
    boolean existsByUserAndAdoptionStatus_ApplicationSuccess(User user);

    @Query("SELECT COUNT(a) FROM Adoption a WHERE a.user = :user AND a.adoptionStatus = 'APPLICATION_SUCCESS'")
    int countAdoptionsByUserAndAdoptionStatus_ApplicationSuccess(User user);

    boolean existsByStray(Stray stray);

}
