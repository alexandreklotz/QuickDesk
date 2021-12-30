# QuickDesk Lite Development Changelog
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
