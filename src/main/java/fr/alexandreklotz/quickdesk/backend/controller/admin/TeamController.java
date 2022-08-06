package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Team;
import fr.alexandreklotz.quickdesk.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TeamController {

    private TeamService teamService;

    @Autowired
    TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/team/all")
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/admin/team/id/{teamId}")
    public Team getTeamById(@PathVariable UUID teamId) throws TeamException {
        return teamService.getTeamById(teamId);
    }

    @GetMapping("/admin/team/name/{teamName}")
    public Team getTeamByName(@PathVariable String teamName) throws TeamException {
        return teamService.getTeamByName(teamName);
    }

    @PostMapping("/admin/team/new")
    public Team createNewTeam(@RequestBody Team team) throws UtilisateurException, TicketQueueException {
        return teamService.createNewTeam(team);
    }

    @PutMapping("/admin/team/update")
    public Team updateTeam(@RequestBody Team team) throws UtilisateurException, TeamException, TicketQueueException {
        return teamService.updateTeam(team);
    }

    @DeleteMapping("/admin/team/delete")
    public void deleteTeam(@RequestBody Team team){
        teamService.deleteTeam(team);
    }
}
