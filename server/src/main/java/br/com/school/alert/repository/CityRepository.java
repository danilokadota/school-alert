package br.com.school.alert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.school.alert.domain.Escola;

public interface CityRepository extends JpaRepository<Escola, Long> {

  @Query("select c from City c where UPPER(c.name) like UPPER(?1) or UPPER(c.state) like UPPER(?1) or UPPER(c.country) like UPPER(?1)")
	Page<Escola> search(String value, Pageable page);

}
