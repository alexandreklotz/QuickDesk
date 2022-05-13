package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import org.apache.catalina.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TeamService {

    public List<Team> getAllTeams();

    public Team getSpecifiedTeam(UUID teamid);

    public Team createNewTeam(Team team);

    public Team updateTeam(Team team);

    public Team updateTeamUsers(Team team, Set<Utilisateur> users);

    public void deleteTeam (Team team);
}
