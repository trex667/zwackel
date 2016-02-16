# Playground for vaadin in combination with jpa

This is a simple example application about creating JPA backed Java EE application
with rich Vaadin based UI.

There are several versions of the same jpa examples with different loading strategies. All are based on the same ER-Model: we have three entities: USER, ADDRESS, ORGANIZATION
### A USER can have many ADDRESSES (oneToMany)
### A USER can have many ORGANIZATIONS and a ORGANIZATION can have many USERS (manyToMany)
All versions follow different strategies to load the data, but the entryPoint is always a service to load all users.
## V1 represents a lazy loading for addresses and organizations 