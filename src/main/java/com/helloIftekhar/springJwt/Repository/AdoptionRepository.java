package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    List<Adoption> findByUserIdOrderByCreatedDateDesc(Integer userId);

    Adoption findByUserIdAndAdoptionId(Integer userId, Long adoptionId);
}
