package com.hodor.apimail.repository;

import com.hodor.apimail.entity.BulkEmail;
import com.hodor.apimail.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkEmailRepository extends JpaRepository<BulkEmail, Long> {
}
