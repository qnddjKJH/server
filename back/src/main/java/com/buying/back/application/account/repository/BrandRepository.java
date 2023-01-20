package com.buying.back.application.account.repository;

import com.buying.back.application.account.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByCompanyRegistrationNumber(String companyRegistrationNumber);
}