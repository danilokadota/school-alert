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
@Table(name = "aluno")
public class Aluno {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;


  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "hora_entrada", nullable = false)
  private Integer horaEntrada;

  @Column(name = "hora_saida", nullable = false)
  private Integer horaSaida;

}
