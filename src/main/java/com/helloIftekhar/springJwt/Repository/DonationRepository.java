package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
