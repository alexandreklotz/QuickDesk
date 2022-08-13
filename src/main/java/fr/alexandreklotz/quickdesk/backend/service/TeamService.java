package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    public boolean isTeamExisting(String teamName);

    public List<Team> getAllTeams();

    public Team getTeamById(UUID teamid) throws TeamException;

    public Team getTeamByName(String name) throws TeamException;

    public Team createNewTeam(Team team) throws UtilisateurException, TicketQueueException, TeamException;

    public Team updateTeam(Team team) throws TeamException, UtilisateurException, TicketQueueException;

    public void deleteTeam (UUID teamId);
}
