package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.TeamException;
import fr.alexandreklotz.quickdesklite.error.TicketQueueException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    public List<Team> getAllTeams();

    public Team getTeamById(UUID teamid) throws TeamException;

    public Team createNewTeam(Team team) throws UtilisateurException, TicketQueueException;

    public Team updateTeam(Team team) throws TeamException, UtilisateurException, TicketQueueException;

    public void deleteTeam (Team team);
}
