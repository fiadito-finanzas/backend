package com.eventos.Fiadito.repositories;

import com.eventos.Fiadito.models.Authority;
import com.eventos.Fiadito.models.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Authority findByName(AuthorityName authority);
}
