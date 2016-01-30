package org.schreibvehler.boundary;

import java.util.List;

import org.apache.deltaspike.data.api.*;

@Repository
public interface UserRepository extends EntityRepository<User, Long> {

    public User findByShortName(String shortName);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses")
    public List<User> findAll();
    
    public QueryResult<User> findById(Long id);
    
    public QueryResult<User> findByShortNameLikeIgnoreCase(String filter);
}
