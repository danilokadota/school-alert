package br.com.school.alert.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pai")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pai{

  @GeneratedValue
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false, updatable = false)
  private String nome;

  @OneToMany(mappedBy = "pai")
  private Set<Aluno> alunos = new HashSet<Aluno>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_ESCOLA") )
  private Escola escola;


}
