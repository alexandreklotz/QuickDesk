package fr.alexandreklotz.quickdesk.backend.service;


import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Utilisateur;

import java.util.List;
import java.util.UUID;

public interface UtilisateurService {

    public boolean isUserExisting(String login);

    public boolean isUserAdmin(String login);

    public List<Utilisateur> getAllUsers();

    public Utilisateur getUserById(UUID userid) throws UtilisateurException;

    public Utilisateur getUserByLogin(String login) throws UtilisateurException;

    public Utilisateur createUser(Utilisateur utilisateur) throws UtilisateurException, TeamException;

    public Utilisateur updateUser(Utilisateur utilisateur) throws UtilisateurException, TeamException;

    public void deleteUserById(UUID userId);
}
