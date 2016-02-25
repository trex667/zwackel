package org.schreibvehler.boundary;

public interface UserService {

    Result<User> findAllUsers();

    Result<User> createTestData(int count);

    Result<Address> findAllAddresses(Integer userId);

    Result<Organization> findAllOrganizations(Integer userId);
}
