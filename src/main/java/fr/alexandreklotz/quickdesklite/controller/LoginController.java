package fr.alexandreklotz.quickdesklite.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    @RequestMapping("/*")
    @RolesAllowed("USER, VIP, ADMIN")
    public String getUser(){
        return "Page utilisateur";
    }

    @RequestMapping("/admin")
    @RolesAllowed("ADMIN")
    public String getAdmin(){
        return "page admin";
    }

}
