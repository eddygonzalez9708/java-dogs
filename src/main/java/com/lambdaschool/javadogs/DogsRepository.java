package com.lambdaschool.javadogs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This interface provides methods to help you create a collection of database objects.
// JpaRepository allows the Dogs class based on the id have access to its methods

public interface DogsRepository extends JpaRepository<Dogs, Long> {
    List<Dogs> findByBreed(String breed);
    List<Dogs> findBySuitable(boolean suitable);
}
