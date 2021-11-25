#QuickDesk Development Follow-Up
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