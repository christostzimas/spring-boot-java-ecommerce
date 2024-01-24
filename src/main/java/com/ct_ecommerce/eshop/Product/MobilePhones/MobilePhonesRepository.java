package com.ct_ecommerce.eshop.Product.MobilePhones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobilePhonesRepository extends JpaRepository<MobilePhones, Integer> {

    @Query("SELECT mp FROM MobilePhones mp WHERE mp.model = :model AND mp.category = 'Mobile Phone'")
    Optional<MobilePhones> findPhoneByModel(@Param("model") String model);
}
