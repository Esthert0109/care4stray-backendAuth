package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByUserIdOrderByUpdatedDateDesc(Integer userId);
    List<Donation> findAllByOrderByUpdatedDateDesc();

    @Query("SELECT d.user FROM Donation d WHERE d.isAnonymously = false ORDER BY d.updatedDate DESC")
    List<User> findAllUserByOrderByUpdatedDateDesc();
}
