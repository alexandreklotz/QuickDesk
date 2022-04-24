# QuickDesk Lite Coding Changelog
***
### 24/04/2022

*Working on HttpSession management and stuff, trying to get something as clean as possible. I'll do some research about cookies to see if they can be of any help
to manage sessions/accesses. I'm currently looking into JWT but it might not be needed. Security now works.*

*Services have been created to externalize some logic and i'll do the same for other controllers in order to simplify them as much as possible.
It will also allow me to have cleaner code and something that works better overall. (i think...)*

*The whole redirection process will be modified, so will be the /mypanel/{login} link. I'd like to find a way to not indicate a user's login in the url
and i'm pretty sure that it's possible.*

* Deletion of `LoginController` -> change of approach regarding the redirection of the users after login. I think it's messing up with the roles and there's cleaner/better ways to do it. The way it was handled wasn't so good.
* __The whole security package has been deleted. It has been redone and it now works as intended. User can't access admin links anymore.__
* Creation of `MyPanelService`. This service will be used to prevent a user to access to another user's panel and redirect him if so, it will also be used to retrieve his panel (tickets etc) and return a modelandview.
* Creation of `TicketService`. This service will replace `TicketServiceController` which has therefore been deleted.
* `AdminPanelController` and `UserPanelController` have been deleted.
* Minor modifications in `UtilisateurRepository`.
***
## 20/04/2022
*Working on the security flaw. Will probably delete the whole security config and start again from scratch. Will probably implement JWT aswell.
I'm currently doing some research about @service. I think that coding the whole logical process in the controllers themselves might not be the best option.
Having services retrieving info instead of controllers might allow me to have more control about how things are supposed to work.*

*This push doesn't update anything except for the changelog and the two HTML templates.*
***
## 21/03/2022
*Focusing on security but still made some minor changes.
Decided to push the modifications to the repo since i want the new entities to be in the software.*

* Repositories for every new entity has been created
* Creation of `TicketQueue` entity.
* New relations have been created between `Team -> TicketQueue and Ticket -> TicketQueue`.
* Controllers have been created for new entities. Only GetMappings have been coded for the meantime.
* Added a variable in `Ticket` -> AssignedAdminName. This variable is a string and it will store the assigned admin's name which will be then displayed in the tickets list on the web page.

*Following the previous changelog notes, i'll need to add a functionality to attach images to a ticket/comment and even a profile picture for each user or something.
It would also be useful for contracts, you'd be able to attach a scanned version of it on the software. I may create new packages aswell to organize a few things better.*
***
## 20/03/2022
*New entities have been created. It was really poor in terms of what it had to offer (only tickets/users/teams/devices)...
These modifications won't be pushed until everything is functional. I'm adding these entities and also looking at the security flaw
which turns out to be worse than i thought. Once logged in, no matter what your role is, you can access the homepage of ANY user/admin.
I suspect the login/redirection process to be the cause of this flaw. It could be the security config aswell.*

* Following entities have been created : `Software`, `Contract`, `Contractor` and `LicenseKey`. Relations have been created aswell. A few fields will need to be created aswell such as dates.
* New JsonViews have been created for each new entity.

*A few fields still need to be created such as Date fields in Contract to indicate the dates/interval/period during which the contract will be in effect.
I need to see how it needs to be formatted through JSON and a web form.*
***
## 18/03/2022
__Tickets are finally retrieved when a user connects !__

Haven't done much today but still reached my main objective. Now that the tickets are retrieved when a user/admin connects, i can focus on the front end and other methods that will need to be developed to make the front end as functional as possible.

* I created `TicketServiceController` which iterates through all the existing tickets and retrieves the tickets for both users and admins in two separate methods.
* The `findOpenedTickets` and `findAssignedTickets` methods in `TicketRepository` have been removed (they are now replaced by the TicketServiceController).
* The methods for the admins' and users' homepage have been modified accordingly (`UserPanelController` and `AdminPanelController`)
* `homepage.html` and `adminpanel.html` are currently being developed. It's only retrieving the user's/admin's name, team and tickets(opened for users, assigned and opened for admins).

*I have a security flaw to check, once a user is logged in he can access the /admin links which displays raw data (JSON). I will focus on this in priority.*
***
## 15/03/2022
__Quick fix has been done in `UtilisateurController`. The PostRequest couldn't update the role because of a coding error. It has been corrected.
Currently doing some research to fix the "parameter value did not match expected type" error when i use the interfaces from TicketRepository to find
tickets linked to users/admins. No changes will be made until i find a way to make them work as intended.__
***
## 22/02/2022 - 2
*Quick modifications, had to check other things again and discovered some things i had forgot to correct/modify*

* The DeleteMapping in the TeamController has been corrected. It wasn't programmed properly, if you tried to delete a team which had users in it it would return an error and wouldn't do anything else. Now the method iterates with a for each in the team's users list and set their team to null through the setTeam setter.
* The PostMapping in UtilisateurController has been corrected. You can now create a user without assigning him in a team. You can assign him when you create the team or later if you just forgot.
* The PostMapping in TicketController has been corrected.

Some other things have been removed, for example some comments in UtilisateurRepository and TicketRepository.
***
## 22/02/2022
`Login redirection currently in development`
* `HomeController` has been refactored -> It is now called `UserPanelController`.
* A new method has been created in `UtilisateurRepository` -> `findUserRoleWithLogin`, this method will be used to find the logged in user's role and redirect him accordingly.
* A new controller has been created : `AdminPanelController`. It will be identical to "UserPanelController" but it will be dedicated to admins with specific methods.
* Creation of a new method in `LoginController` -> loggedUserRedirection.  This method allows us to redirect the logged in user to the page matching his role by using the new method created in UtilisateurRepository as mentioned above.
* Interfaces in `TicketRepository` have been corrected -> They were returning only one ticket. They will now return a List of tickets.
* `toString` method has been corrected in the `Utilisateur` entity and it has been created in the `Roles`, `Device` and `Team` entities aswell. (will probably be useless but still coded them in just in case. can still remove them later).
* `SpringSecurityConfig` has been modified in order to implement redirection.

Redirection is working. Next step, front end.
UUIDs need to be migrated to Strings. UUIDs are causing errors when i try to retrieve tickets opened by specific users through the interfaces i coded in the ticketrepository for example.
It works fine if i use the Getter linked to the tickets/utilisateur entity but i want to use the interface, i can't use the findTicketsAssigned interface and i have no other way to retrieve them.
***
## 17/02/2022
`Have been pretty much playing around to try and find a way to redirect the logged in user to his personal homepage (/home/user login) but haven't figured it out yet.
I therefore played around a bit with basic authentication and retrieved basic elements and displayed them in a raw html page (testpanel in homecontroller).`
* Deletion of `LoginController`.
* `configuration` package has been renamed to `security`.
* Added comments in the controllers.

I struggle with security. Login works fine but i would like to redirect each user to a custom page. For example, if i'm a user with the login "aklotz", i want to
be redirected to "ip_address/aklotz" or "ip_address/panel/aklotz". And if i would be an admin with the login "admklotz", i want to be redirected to
"ip_address/admin/aklotz" or just /admin. Maybe i don't need to specify the user/admin's login after the /home or /panel url to access his created/assigned
tickets. I tried to create a AuthenticationSuccessHandler but it didn't work therefore i deleted it.
I'm not working on my project a lot recently, mainly because i pretty much "lost" myself in this security thing and i also need to do other IT things on the side.

Quick note : I need to see if i can implement a device history in which all its previous users would be stored with the date at which they used the device.
***
## 07/02/2022
* Modification of the relation between "Device" and "Utilisateur". It was a OneToMany and it now is a OneToOne.
* @Override toString method created in "Utilisateur".
* "QueueView" has been deleted in "CustomJsonView".
* The URL to retrieve a specific ticket was previously restricted to admins only. It has been modified and will now be accessible by all users for functionality purposes.
***
## 04/02/2022
* Creation of a new method in "TicketRepository" : findTicketsAssigned. This method will find tickets assigned to the specified UUID (admins).
* Creation of a new method in "TicketRepository" : findTicketsOpened. Used to find tickets created by each user.
* The POST Method in "TicketController" has been modified -> A responseentity.ok in an if was preventing the whole method to execute. It would stop at the responseentity.ok.

Not much progress today again, tried to mess around with thymeleaf. No push.
***
## 03/02/2022
`Thymeleaf dependency has been added.`
* Creation of "HomeController'. This controller will be used to retrieve basic user info such as his name, team, and tickets. If the user is an admin, it will retrieve all the tickets he's assigned to.

Not much have been done therefore i won't push the code today.
***
## 01/02/2022
`Security implementation is nearly finished even though it remains quite basic.`
* SpringSecurityConfig has been modified -> CorsConfiguration has been created.
* Quick modification in "UtilisateurController" at Line 86. Update of the default role assignment when a user is created without a role.
* CustomJsonView.Utilisateur.class has been added to "roleName" in "Roles".
* OAuth2.0 has been removed in pom.xml. This dependency wasn't supposed to be added and it wont be used for the "Lite" version.
* "utilMailAddr" in the "Utilisateur" model is now a nullable variable.
* Links in controllers have been updated. "/admin" has been added to admin restricted methods, "/user" to user restricted methods.
* The "DeleteMapping" in "CommentController" has been modified to allow the user who wrote this comment to delete it. The user won't be able to delete comment that he didn't write. Admins can delete all comments.
<br>

Controllers will be reorganized. There will be a front controller that will retrieve infos/tickets or specific things depending
of the user's role. Some methods such as ticket and comment creation will be copied in a controller accessible to users with slightly
different code in order to define what users can do and what they can't do (users can't close or delete tickets for example).
***
## 31/01/2022
* Creation of `UserDetailsServiceCustom` -> Security implementation
* Creation of `UserDetailsCustom` -> Security implementation
* "service" package has been deleted.

The next step is the implementation of JWTs. The methods on each controller will be modified accordingly.
Each PostMapping link will be modified aswell in order to restrict some requests to admins only and others to users.
***
## 28/01/2022
* An antMatcher has been added in the security config
Haven't done anything else, next modifications will be linked to the implementation of the security module.
I'm still documenting myself about it since there are a few things that i don't know well.
***
## 27/01/2022
`Security implementation still ongoing and i'm also reading my previous classes again in order to solve the
minor issues i'm facing and get the whole thing working as intended.`
* The SQL query in "SpringSecurityConfig" to retrieve users from the database has been updated following the implementation of "Roles".
* A quick fix has been done in "TeamController" within the PutMapping to update an existing team.

Have tried a few things but i can't send any kind of request with Postman since i implemented security. It works totally fine
when i access my API from my browser but postman apparently refuses to authenticate and it keeps returning me the
login page html code.
__Today's work will not be pushed since only minor changes have been done.__
***
## 25/01/2022
`Security implementation still ongoing.`
* The "UserType" management in the Post and Put Mappings in "UtilisateurController" have been updated -> The usertype has been replaced by the new Roles entity
* Some variables have been refactored.
* New boolean has been created in the "Team" entity -> techTeam. This boolean will allow the admins to create specific technical teams. This boolean will also be used to filter teams that can handle tickets.
* New UUID value "assignedAdmin" has been created in the "Ticket" entity. This variable will contain the assigned admin's UUID.
* The "TECH" role has been removed. There will only be admins.
* The "ManyToMany" between "Utilisateur" and "Ticket" has been modified to a "ManyToOne / OneToMany". The "assignedAdmin" value is the main reason for this change.

I decided to finally push my work to github since i will not rollback these changes. Security should be working fine but i can't authenticate
on my API through postman for some reason. I therefore can't send JSON forms to my API with this tool. Once i will have found a way to get postman to work,
i'll finish my testing and finalize some requests. I will then finalize security and start developing the front-end.
***
## 22/01/2022
`Security implementation is ongoing. Most of the modifications below have been done as a consequence of that.`<br>
* "Admn" and its controller/repository/relations have been deleted, it will definitely generate issues with security.
* The relation between "Ticket" and "Utilisateur" will be modified to a ManyToMany.
* "utilEnabled" boolean has been created in the "Utilisateur" entity. The PostMapping to create a new user has been modified accordingly.
* Creation of a new entity "Roles". It will be required to retrieve the role of each user to get the security to work properly. The UserType enum and AdmnType enum have been deleted, this Roles entity will contain the 4 user types : vip, user, tech and admin.
* "AdmnView" in CustomJsonView has been replaced by "RolesView"
* Creation of "import.sql". It will allow me to populate the database with default users etc. The "application.properties" file has been modified to generate the schema.
* The "creationDate" property in "Utilisateur" is now nullable. The frontend and PostMapping will be setup in a way to automatically add the creation date in this field.
* The Post and Put Mappings in the "TicketController" have been updated accordingly to the ManyToMany relation between "Utilisateur" and "Ticket".
***
## 18/01/2022
`Implementation of spring security has been started on this day.`
* Security dependencies have been added in the pom.xml file.
* Creation of a new package : `Configuration`. This package will contain all the config files for the security dependencies.
* A passwordencoder bean has been created in the SpringSecurityConfig file. All users/admins passwords will be encrypted when created.

Today's work hasn't been pushed to github since i'm still experimenting. I don't want to rollback the repo if anything goes wrong.
A few things are still unclear to me regarding security and stuff, i need to see how i can use JDBC users to login.
I might need to delete the "Admn" entity and its controller/repository, it may cause trouble regarding security implementation. It would also simplify
the way this software will work. A few methods will need to be modified if i delete this entity but nothing impossible.
I think that i'll also keep using UUIDs for "Utilisateur" and "Team".
***
## 17/01/2022
* The PostMethod in "CommentController" has been slightly modified to return a ResponseEntity depending on the outcome of the request.
* "admnMailAddr" field added in Admn class. This field and the admnLogin field have been set as unique fields to avoid duplicates.
* "utilLogin" and "utilMailAddr" have been set as unique fields to avoid duplicates.
* ID field in "Team" has been migrated from Long to UUID.
* Created a "findWithLogin" in "AdmnRepository" and "UtilisateurRepository" to verify that the login from the JSON form doesn't already exist. I had to do this since Admn and Utilisateur are two different entities.

All the functionalities are working as intended.
***
## 13/01/2022
* The ID field in Utilisateur has been migrated from Long to UUID.
* The post method in CommentController has been modified. It hasn't been tested yet but it should work. It will now use the UUID to determine if the user creating a comment is either a user or an admin.
* Other quick fixes and modifications have been done.

`This changelog is quite short because i had to rollback the project following some changes that needed to be reverted.`
***
## 06/01/2022
* A few modifications in the TicketController have been done. The user which owns the ticket is now verified by the repository findById method before assigning it to the ticket. If it doesn't exist, it will return an error. The method type has been modified from "void" to "ResponseEntity".
* The same modification has been done in other controllers aswell. The "void" method type for Post requests have been modified to ResponseEntity to keep the user/admin updated if everything worked properly or not.
* New String value has been created in the "Utilisateur" entity => utilMailAddr. This String will be used to store the user's mail address to send him notifications.
* Creation of a new class : "Admn" (Model, Controller & Repository). This class has been created because assigning a technician/admin to a ticket was impossible before unless we would change the ticket owner. Now, the ticket can be owned by the user and it can be assigned to a tech/admin __at the same time__.
* Creation of a new LocalDateTime variable in the "Ticket" model => ticketLastModified. Everytime a ticket will be modified, the lastModified variable will be updated to the time at which the update has been made.
* The Post method in TicketController was generating an HTTP error following the implementation of the Admn object. It has been corrected.

The creation of a new boolean to restrict teams to only admins or only users will be created (or not, a team could have admins and users).
I need to find a way to manage comments for both entities. I can add comments as a user and as an admin but with two different requests (one for each entity). There could be an easy fix for this,
and it would be to create a ManyToMany between "Utilisateur" and "Comments". I would then create a ManyToMany between "Utilisateur" and "Ticket" aswell and delete the new "Admn" class and reimplement the old enum in the "Utilisateur"
model with the following values : USER, TECH, ADMIN and VIP.<br>
About the Admn class, there's only one role currently which is ADMIN, i'm thinking about implementing a "TECH" role which would be like a low level admin. I'm thinking about it but idk if it's necessary.
`I'm currently looking into Spring Security. These small fixes/changes are needed, once everything will be clean i'll start implementing it`.
***
## 04/01/2022
* README.md update
* DeleteMapping & PutMapping links in UtilisateurController have been corrected. (previously `/user/...` instead of `/utilisateur/...`)
* Creation of a new class => Device. Its controller and repository have been created aswell as its relation with "Utilisateur".
* "DeviceView" added in CustomJsonView
* "TeamController" PostMapping/PutMapping have been modified. A few other methods have been fixed, there was a few things to correct here and there.
* Dates have been migrated to LocalDateTime. The JsonFormat property has also been added and dates are now properly formatted.

All the methods in each controller is working as intended. __Security is now the next step__.
***
## 03/01/2022
Not much has been done today, __just a few fixes/changes__. Comments are working fine, they appear in the ticket as wanted.
__I will start documenting myself about spring security before adding security in my project__.
Once the security will be properly implemented, i will start developing the front-end. I'll probably use thymeleafs
for that and i'll use Angular for the "full" version.

* Removal of lombok dependency -> Was causing an error with getters and setters when i would use a Set in a relation
* All List have been migrated to Set
* Constructors, Getters and Setters have been created in each class
* Created GetMappings in all controllers to get a specific object by specifying its id in the link. For example, you only want to retrieve the info about the utilisateur with the id 1 -> /utilisateur/1
***
## 30/12/2021
* Created a new class : Comment. Users, technicians and admins will be able to add comments in a ticket. I will work on the possibility of adding images or documents in a comment.
* The controller and the repository have been created for the new "Comment" entity.
* The relation between Comment and Ticket has been created.
* All the PostMappings have been created in the CommentController.
* A few TODOs have been added, a few functionalities need to be clarified/defined properly to be implemented later in the software's logic.

***
## 28/12/2021
* Creation of PutMappings to update existing objects such as Users, Tickets and Teams. Methods are functional after testing. Ifs have been implemented in the linked methods to avoid empty fields.
* DeleteMappings have been created for each class.
* "utilLogin" field has been added in the "Utilisateur" class. This variable will be used by the user to login and will also be used for security functionalities.
* SpringSecurity dependency has been added in pom.xml. I will start documenting myself and follow a course before implementing security in my software.

PutMappings will be restricted to specific roles. The "updateTicket" method linked to the PutMapping in the TicketController will be split in two or the user will only
be able to add/modify comments once the ticket is created. Only a technician or an admin will be able to modify all its fields.
All the other PutMappings linked to update methods will be restricted to technician/admin only to avoid any security issues.

***
## 23/12/2021
* Creation of the three main models : Utilisateur, Ticket, Team
* Creation of CustomJsonView
* application.properties has been setup
* Relations between entities have been created
* "UtilisateurController", "TicketController" and "TeamController" have been created.
* Get and Post methods are working in all controllers

The "USER, TECH, ADMIN" roles need to be associated with specific methods (user creation and deletion, ticket edition, etc). Once it will be done,
i will start to setup security.
***
