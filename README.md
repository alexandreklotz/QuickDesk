# QuickDesk Lite

This ticketing solution is a personal project. Its main purpose is for me to acquire back-end/spring development skills.
I called this software QuickDesk __Lite__ because once this software will be finished (MVC, Security, front-end) i will add functionalities
and make a "full" version.
For now, it's quite simple and consists of three models : Utilisateur, Team, Ticket. Each model has its Repository and Controller.
Each controller has a Get, Post, Put and Delete method. Get is used to retrieve all the objects from a specific class, Post to create new objects,
Put to update them and Delete to delete objects. I guess that i could restructure my controllers and make the methods more efficient. I could also only use
PutMappings to both create and update objects.