package com.example.nnzcrawling.repository;

import com.example.nnzcrawling.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByTitleContaining(String title);

    Optional<Show> findByTitleAndStartDateAndIsDeleteFalse(String title, String startDate);

    List<Show> findAllByCreatedAtAfter(LocalDateTime startTime);

    List<Show> findAllByIsDeleteFalse();
}
