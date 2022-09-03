package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.TeamException;
import fr.alexandreklotz.quickdesk.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.model.Team;
import fr.alexandreklotz.quickdesk.service.TeamService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/admin/team/all")
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/admin/team/id/{teamId}")
    public Team getTeamById(@PathVariable UUID teamId) throws TeamException {
        return teamService.getTeamById(teamId);
    }

    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/admin/team/name/{teamName}")
    public Team getTeamByName(@PathVariable String teamName) throws TeamException {
        return teamService.getTeamByName(teamName);
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/admin/team/create")
    public Team createNewTeam(@RequestBody Team team) throws UtilisateurException, TicketQueueException, TeamException {
        return teamService.createNewTeam(team);
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PutMapping("/admin/team/update")
    public Team updateTeam(@RequestBody Team team) throws UtilisateurException, TeamException, TicketQueueException {
        return teamService.updateTeam(team);
    }

    @JsonView(CustomJsonView.TeamView.class)
    @DeleteMapping("/admin/team/id/{teamId}/delete")
    public void deleteTeam(@PathVariable UUID teamId){
        teamService.deleteTeam(teamId);
    }
}
