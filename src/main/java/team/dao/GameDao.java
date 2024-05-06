package team.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.entity.Game;

public interface GameDao extends JpaRepository<Game, Long> {

}
