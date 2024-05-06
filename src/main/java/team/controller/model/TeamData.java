package team.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import team.entity.Game;
import team.entity.Player;
import team.entity.Team;

//DTO class
@Data
@NoArgsConstructor
public class TeamData {

  private Long teamId;
  private String teamName;
  private String teamMascot;
  private String teamColors;
  private String teamLocation;

  private Set<GameData> games = new HashSet<GameData>();

  private Set<PlayerData> players = new HashSet<PlayerData>();
  
  public TeamData(Team team) {
    teamId = team.getTeamId();
    teamName = team.getTeamName();
    teamMascot = team.getTeamMascot();
    teamColors = team.getTeamColors();
    teamLocation = team.getTeamLocation();
    
    for(Game game : team.getGames()) {
      games.add(new GameData(game));
    }
    
    for(Player player : team.getPlayers()) {
      players.add(new PlayerData(player));
    }
  }

//Inner DTO class
@Data
@NoArgsConstructor
public static class PlayerData {
  private Long playerId;
  private String playerFirstName;
  private String playerLastName;
  private String playerPosition;
  
  public PlayerData(Player player) {
    playerId = player.getPlayerId();
    playerFirstName = player.getPlayerFirstName();
    playerLastName = player.getPlayerLastName();
    playerPosition = player.getPlayerPosition();
  }
}

//Inner DTO class
@Data
@NoArgsConstructor
public static class GameData {
  private Long gameId;
  private String location;
  private String finalScore;
  private String winner;
  
  public GameData(Game game) {
    gameId = game.getGameId();
    location = game.getLocation();
    finalScore = game.getFinalScore();
    winner = game.getWinner();
  }
}

}
