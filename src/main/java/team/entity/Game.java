package team.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gameId;
  
  private String location;
  private String finalScore;
  private String winner;
  
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(mappedBy = "games", cascade = CascadeType.PERSIST)
  private Set<Team> teams = new HashSet<Team>();
}
