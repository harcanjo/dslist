package com.harcanjo.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harcanjo.dslist.entities.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long>{
}
