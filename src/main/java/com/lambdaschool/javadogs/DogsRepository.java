package com.lambdaschool.javadogs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogsRepository extends JpaRepository<Dogs, Long> {
}
