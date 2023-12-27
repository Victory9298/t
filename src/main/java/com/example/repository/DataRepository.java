package com.example.repository;

import com.example.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {
    @Query("SELECT d FROM Data d ORDER BY d.death DESC limit 5")
    List<Data> findTop5CountriesByDeath();

    @Query("SELECT d FROM Data d ORDER BY d.active DESC limit 5")
    List<Data> findTop5CountriesByActive();

    @Query("SELECT d FROM Data d ORDER BY d.active DESC limit 5")
    List<Data> findTop5CountriesByRecovered();

    @Query("SELECT sum(d.death) FROM Data d")
    Integer findTotalDeath();

    @Query("SELECT sum(d.active) FROM Data d")
    Integer findTotalActive();

    @Query("SELECT sum(d.recovered) FROM Data d")
    Integer findTotalRecovered();
}
