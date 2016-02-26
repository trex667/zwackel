# Playground for vaadin in combination with jpa

This is a simple example application about creating JPA Java EE application with rich Vaadin based UI.

There are several versions of the same jpa examples with different loading strategies. All are based on the same ER-Model: we have three entities: USER, ADDRESS, ORGANIZATION
### A USER can have many ADDRESSES (oneToMany)
### A USER can have many ORGANIZATIONS and a ORGANIZATION can have many USERS (manyToMany)
All versions can be reached by adding #!V[number] at the end of the URL. The views show a table with all persisted users and you can open a detail window by clicking on a row. All versions follow different strategies to load the data, but the entryPoint is always a service to load all users. You can see the time to load the data in the label above the table in each screen.
## V1 represents a LAZY loading for addresses and organizations
Only all users are loaded when you enter the screen and the related addresses/organizations of the user are loaded when you open the details
## V2 represents a EAGER loading for addresses and organizations just by setting the corresponding attributes to EAGER
All users, all addresses and all organizations are loaded when you enter the screen
## V3 represents a EAGER loading for addresses and organizations by calling the size() methods of addresses and organizations
All users, all addresses and all organizations are loaded when you enter the screen