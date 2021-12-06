#QuickDesk Development Follow-Up
***
##06/12/2021
* All the remaining controllers have been created (license, software, ticket, role, permission, contractor). Basic rest methods have been implemented
* Comments have been added in a few classes.
* Rest methods created in SoftwareController, TicketController, LicenseController.
* RoleController and PermissionController will be finalized later.

Rest methods in the controllers are __REALLY BASIC__. They will be improved, PatchRequests will be implemented later, and i may implement a server sided confirmation when sending a delete request to avoid any user mistake (or client sided).
I will finalize the PatchMappings and also a few details in the controllers. Once done, i will start implementing security.
***
##03/12/2021
* @Repository annotation added in all dao
* Created "ContractController" with basic rest methods => still need to be tested
* Created "ContractorController" with basic rest methods => Still need to be tested
* Created "DeviceController" with basic rest methods => Still need to be tested. I will start developing a small python agent/script to retrieve the device info and sent them to the API (but first, it will only retrieve info without sending it. I'd like to get all my rest requests to work first)
* Corrected a few mistakes in the "Device" class

Spent much time trying to get the PatchRequest in UserController to work but without any success. Haven't made much progress yet again...
***
##02/12/2021
* Getters, setters and constructors have been removed in each class => Lombok has been implemented, therefore i just needed to add annotations for lombok to generate these automatically
* Column(nullable = false) has been set in the device model for two fields
* TicketStatus enum has been created in the ticket class to assign a status to the ticket
* TicketType enum has been created in the ticket class to assign a type to the ticket
* TicketCategorization enum has been created in the ticket class to assign a category to the ticket
* TicketPriority enum has been created in the ticket class to assign a priority to the ticket
* User creation is now working 100%, it was impossible to assign a team to the user when sending a JSON form from postman to the api but the @JoinColumn wasn't specified in the user model...(__facepalm__).

Didn't make many changes nor much progress today, have done quite some research again, was planning to change a few models from entity to embeddable but it will remain as it is for the time being.
I will keep on working on controller methods to get all the default functionalities to work (team update/deletion, user update, tickets, etc...). I'll create the security config once all the controller methods will be working
or atleast when the user and team controller will be complete (BCryptPassword at first and then role restricted URLs).
I will also develop a python script/agent that will retrieve all the device peripherals/info and send it to the api to automatically create the device with the correct system info and link it to the current user.
But, i'll probably need to find a way to manage how to authenticate the user through the agent or something considering that the users are users from the software database and not LDAP users.
I'll also do some research later on to see how i can manage LDAP authentication and even implement SSO or something like that. But it will be __WAY__ later.
***
##26/11/2021

* application.properties has been modified => the database name in the url has been modified and ddl-auto now __updates instead of creating__.
* The boolean "isTeamEnabled" in the Team model has been refactored to "teamEnabled"
* The boolean "isRoleCanBeDeleted" in the Role model has been refactored to "roleCanBeDeleted"
* The boolean "isUserEnabled" in the User model has been refactored to "userEnabled"
* Creation of the following classes and their daos : Contract, Contractor, Software, License. The relations for each have been created aswell.
* Most of the relations "List" have been migrated to "Set" in the User and Team classes.
* There is an issue when creating a user, it won't assign it in a team and it won't update the team members list. Post methods are still being modified to solve this issue but have been "disabled" for the time being.
* Classes have been added in CustomJsonView

Lots of research has been done today, especially for the post methods that won't assign the group to a user and vice versa. Once this issue will be solved, the remaining functions etc should be developed quickly.
Just a quick note, all the models are currently entities. Some of them may be converted to @Embeddable classes.

***
##25/11/2021
The roles will have permissions that will be attributed to each team. That means that a user will automatically and always be a normal user when he registers or when he is created.
He will only be admin through team permissions. A superadmin account will be created though.
This might change in the future, we're still at a very early stage of development right there :)

* Permission class and Permission Dao have been added.
* Permission relations with other classes (Role only) have been created
* ManyToMany relation with Permission has been added in Role.
* Relation between Group and Role has been created.
* All the relations that i needed to create have been created (may have forgot to list one or two above)
* Some relations have been corrected (joinColumn and inverseJoinColumn settings mostly)
* Application properties setup
* Line 48 : from private User user to private Group team. This mistake has been corrected.
* Creation of a controller for User. Three methods have been created for testing purposes : Get, Post and Delete.
* Creation of a controller for Team. Identical as UserController.
* Group has been refactored. It is now called "Team".
* (nullable = false) has been added in the @column annotation for specific fields.
* The database is properly created when the server is started.
* Boolean "isEnabled" has been added in the User model. If true, the user can be deleted.
* Boolean "isRoleCanBeDeleted" has been added in the Role model. If true, the role can be deleted. May be removed or modified later on depending on how it works.
* Boolean "isTeamEnabled" has been added in the Team model. If true, the team is enabled and active. May be removed or modified later.

***
##24/11/2021
Creation of models and interfaces. Relations have been created for the following models :
* Ticket => Group
* Ticket => User
* User => Device
* Device => Ticket

The roles need to be implemented. At first, i was planning to assign them through
teams but i think that the best way to implement them is by assigning them
directly to the user either with a boolean or an ENUM (or even another solution).
There will be an admin or IT Team team with admin rights. Each team can have
its ticket entity and will therefore be able to manage every ticket __IN AND ONLY
in their entity (unless a user is an admin)__.

The way the roles will work __need to be redefined__, if they're either assigned through
teams or directly to the user and how they will be assigned.
I need to see if multiple "levels" can be generated such as moderator, admin,
superadmin, etc...