#QuickDesk CHANGELOG
***
##24/11/2021
Creation of models and interfaces. Relations have been created for the following models :
* Ticket => Group
* Ticket => User
* User => Device
* Device => Ticket

The roles need to be implemented. At first, i was planning to assign them through
groups but i think that the best way to implement them is by assigning them
directly to the user either with a boolean or an ENUM (or even another solution).
There will be an admin or IT Team group with admin rights. Each group can have
its ticket entity and will therefore be able to manage every ticket in __AND ONLY
in their entity (unless a user is an admin)__.

The way the roles will work __need to be redefined__, if they're either assigned through
groups or directly to the user and how they will be assigned.
I need to see if multiple "levels" can be generated such as moderator, admin,
superadmin, etc...