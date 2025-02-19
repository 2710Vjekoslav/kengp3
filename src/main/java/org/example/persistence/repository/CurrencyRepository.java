package org.example.persistence.repository;

import org.example.persistence.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCodeAndLocale(String code, String locale);

    @Modifying
    @Transactional
    @Query("DELETE FROM Currency c WHERE c.id = :id")
    int deleteCurrencyById(Long id);
}