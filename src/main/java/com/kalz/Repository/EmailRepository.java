package com.kalz.Repository;

import com.kalz.Model.EmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailRecord, Long> {
}
