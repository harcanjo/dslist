package com.harcanjo.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harcanjo.dslist.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
}
