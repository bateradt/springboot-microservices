package com.monitora.aulamicroservices.core.repository;

import com.monitora.aulamicroservices.core.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {
    public ApplicationUser findByUsername(String username);

}
