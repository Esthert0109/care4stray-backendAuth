package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByUserIdOrderByUpdatedDateDesc(Integer userId);
    List<Donation> findAllByOrderByUpdatedDateDesc();

    List<Donation> findAllByStatusOrderByUpdatedDateDesc(DonationStatus status);

    @Query("SELECT d.user FROM Donation d WHERE d.isAnonymously = false ORDER BY d.updatedDate DESC")
    List<User> findAllUserByOrderByUpdatedDateDesc();

    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.createdDate BETWEEN :startDate AND :endDate")
    Double sumByCreatedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
