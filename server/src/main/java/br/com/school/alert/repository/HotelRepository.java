package br.com.school.alert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.school.alert.domain.Escola;
import br.com.school.alert.domain.Aluno;

public interface HotelRepository extends JpaRepository<Aluno, Long> {

  @Query(value = "select h from Hotel h left join fetch h.city c where UPPER(c.name) like UPPER(?1) or UPPER(c.state) like UPPER(?1) or UPPER(c.country) like UPPER(?1) or UPPER(h.name) like UPPER(?1) or UPPER(h.address) like UPPER(?1) or UPPER(h.zip) like UPPER(?1)", countQuery = "select count(h) from Hotel h left join h.city c where UPPER(c.name) like UPPER(?1) or UPPER(c.state) like UPPER(?1) or UPPER(c.country) like UPPER(?1) or UPPER(h.name) like UPPER(?1) or UPPER(h.address) like UPPER(?1) or UPPER(h.zip) like UPPER(?1)")
	Page<Aluno> search(String like, Pageable page);

  @Query("select h from Hotel h left join fetch h.city c where h.id = ?1")
	Aluno findByIdWithCity(Long id);

  Long countByCity(Escola city);

}
