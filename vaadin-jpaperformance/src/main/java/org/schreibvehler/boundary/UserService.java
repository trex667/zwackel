package org.schreibvehler.boundary;

public interface UserService {

    Result<User> findAllUsers();

    default Result<User> findAllUsers(Integer start, Integer fetchSize) {
        return findAllUsers();
    }

    Result<User> createTestData(int count);

    Result<Address> findAllAddresses(Integer userId);

    Result<Organization> findAllOrganizations(Integer userId);
}
