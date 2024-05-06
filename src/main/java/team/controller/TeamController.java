package team.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import team.controller.model.TeamData;
import team.controller.model.TeamData.GameData;
import team.controller.model.TeamData.PlayerData;
import team.service.TeamService;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

  @Autowired
  private TeamService teamService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public TeamData insertTeam(@RequestBody TeamData teamData) {
	log.info("Creating team{}", teamData);
	return teamService.saveTeam(teamData);
  }

  @PutMapping("/{teamId}")
  public TeamData updateTeam(@PathVariable Long teamId, @RequestBody TeamData teamData) {
	teamData.setTeamId(teamId);
	log.info("Updating team {}", teamData);
	return teamService.saveTeam(teamData);
  }
  
  @GetMapping("/team")
  public List<TeamData> retrieveAllTeams() {
	log.info("Retrieve all teams on roster");
	return teamService.retrieveAllTeams();
  }
  
  @GetMapping("/{teamId}")
  public TeamData retrieveTeamById(@PathVariable Long teamId) {
	log.info("Retrieving team with ID=", teamId);
	return teamService.retrieveTeamById(teamId);
  }
  
  @DeleteMapping("/{teamId}")
  public Map<String, String> deleteTeamById(@PathVariable Long teamId) {
	log.info("Deleting team with ID=" + teamId + " was successful.");
	teamService.deleteTeamById(teamId);
	return Map.of("message", "Deletion of team with ID=" + teamId + " was succesful");
  }
  
  @PostMapping("/{teamId}/player")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PlayerData insertPlayer(@PathVariable Long teamId, @RequestBody PlayerData playerData) {
	log.info("Creating player {}", playerData);
	return teamService.savePlayer(teamId, playerData);
  }
  
  @PostMapping("/{teamId}/game")
  @ResponseStatus(code = HttpStatus.CREATED)
  public GameData insertGame(@PathVariable Long teamId, @RequestBody GameData gameData) {
	log.info("Creating game {}", gameData);
	return teamService.saveGame(teamId, gameData);
  }
}
