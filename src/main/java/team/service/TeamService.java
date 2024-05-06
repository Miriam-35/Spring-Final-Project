package team.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.controller.model.TeamData;
import team.controller.model.TeamData.GameData;
import team.controller.model.TeamData.PlayerData;
import team.dao.GameDao;
import team.dao.PlayerDao;
import team.dao.TeamDao;
import team.entity.Game;
import team.entity.Player;
import team.entity.Team;

@Service
public class TeamService {

  @Autowired
  private TeamDao teamDao;
  
  @Autowired
  private PlayerDao playerDao;
  
  @Autowired
  private GameDao gameDao;

  @Transactional(readOnly = false)
  public TeamData saveTeam(TeamData teamData) {
    Long teamId = teamData.getTeamId();
    Team team = findOrCreateTeam(teamId);
  
    copyTeamFields(team, teamData);
    return new TeamData(teamDao.save(team));
}

  private void copyTeamFields(Team team, TeamData teamData) {
    team.setTeamId(teamData.getTeamId());
    team.setTeamName(teamData.getTeamName());
    team.setTeamMascot(teamData.getTeamMascot());
    team.setTeamColors(teamData.getTeamColors());
    team.setTeamLocation(teamData.getTeamLocation());
  }

  private Team findOrCreateTeam(Long teamId) {
    Team team;
  
    if(Objects.isNull(teamId)) {
	  team = new Team();
    }
    else {
	  team = findTeamById(teamId);
    }
	  return team;
}

  private Team findTeamById(Long teamId) {
	  return teamDao.findById(teamId).orElseThrow(() -> new NoSuchElementException("Team with ID=" + teamId + " was not found"));
  }
  
  @Transactional
  public List<TeamData> retrieveAllTeams() {
    List<Team> teams = teamDao.findAll();
    List<TeamData> result = new LinkedList<TeamData>();
    
    for(Team team : teams) {
      TeamData newTeamData = new TeamData(team);
      
      newTeamData.getPlayers().clear();
      newTeamData.getGames().clear();
      
      result.add(newTeamData);
    }
	  return result;
}

  @Transactional
  public TeamData retrieveTeamById(Long teamId) {
    Team team = findTeamById(teamId);
	return new TeamData(team);
}

  @Transactional
  public void deleteTeamById(Long teamId) {
    Team team = findTeamById(teamId);
    teamDao.delete(team);
}

  @Transactional(readOnly = false)
  public PlayerData savePlayer(Long teamId, PlayerData playerData) {
	Team team = findTeamById(teamId);
	Long playerId = playerData.getPlayerId();
	Player player = findOrCreatePlayer(teamId, playerId);
	
	copyPlayerFields(player, playerData);
	
	player.setTeam(team);
	
	team.getPlayers().add(player);
	
	return new PlayerData(playerDao.save(player));
}

  private void copyPlayerFields(Player player, PlayerData playerData) {
    player.setPlayerId(playerData.getPlayerId());
    player.setPlayerFirstName(playerData.getPlayerFirstName());
    player.setPlayerLastName(playerData.getPlayerLastName());
    player.setPlayerPosition(playerData.getPlayerPosition());
}

  private Player findOrCreatePlayer(Long teamId, Long playerId) {
    Player player;
    
    if(Objects.isNull(playerId)) {
      player = new Player();
    }
    else {
      player = findPlayerById(teamId, playerId);
    }
	return player;
}


  private Player findPlayerById(Long teamId, Long playerId) {
    Player player = playerDao.findById(playerId).orElseThrow(() -> new NoSuchElementException("Player with ID=" + playerId + " does not exist."));
	
    if(player.getTeam().getTeamId() != teamId) {
      throw new IllegalArgumentException("Player with ID=" + playerId + " does not play for team with ID=" + teamId);
    }
    return player;
}


  @Transactional(readOnly = false)
  public GameData saveGame(Long teamId, GameData gameData) {
    Team team = findTeamById(teamId);
    Long gameId = gameData.getGameId();
    Game game = findOrCreateGame(teamId, gameId);
    
    copyGameFields(game, gameData);
    
    game.getTeams().add(team);
    
    team.getGames().add(game);
    
	return new GameData(gameDao.save(game));
}


  private void copyGameFields(Game game, GameData gameData) {
    game.setGameId(gameData.getGameId());
    game.setLocation(gameData.getLocation());
    game.setFinalScore(gameData.getFinalScore());
    game.setWinner(gameData.getWinner());
}

  private Game findOrCreateGame(Long teamId, Long gameId) {
    Game game;
    
    if(Objects.isNull(gameId)) {
      game = new Game();
    }
    else {
      game = findGameById(teamId, gameId);
    }
	return game;
}

  private Game findGameById(Long teamId, Long gameId) {
    Game game = gameDao.findById(gameId).orElseThrow(() -> new NoSuchElementException("Game with ID=" + gameId + " does not exist."));
    
    boolean found = false;
    
    for(Team team : game.getTeams()) {
      if(team.getTeamId() == teamId) {
    	found = true;
    	break;
      }
    }
      if(!found) {
    	throw new IllegalArgumentException("This team with ID=" + teamId + " did not play in game with ID=" + gameId);
      }
	  return game;
}
}
