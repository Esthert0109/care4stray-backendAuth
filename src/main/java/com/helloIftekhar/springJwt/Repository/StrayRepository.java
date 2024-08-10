package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Stray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StrayRepository extends JpaRepository<Stray, Long> {
    List<Stray> findAllByOrderByUpdatedDateDesc();

    @Query("SELECT s FROM Stray s WHERE s.status = 'AVAILABLE' OR s.status = 'RETURNED' ORDER BY s.updatedDate DESC")
    List<Stray> findAllStray();
}
