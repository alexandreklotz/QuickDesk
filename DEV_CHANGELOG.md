# QuickDesk Lite Coding Changelog
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
