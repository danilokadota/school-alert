package br.com.school.alert.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "escola")
public class Escola {

  @GeneratedValue
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false, length = 200)
  private String nome;

  @Column(name = "endereco", nullable = false, length = 100)
  private String endereco;

  

}
