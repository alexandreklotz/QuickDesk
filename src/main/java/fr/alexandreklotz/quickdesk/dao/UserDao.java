package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

}