package br.com.school.alert.controller.dto;

import org.springframework.security.core.authority.AuthorityUtils;

import br.com.school.alert.security.DefaultUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

  private Long id;
  private String name;
  private Boolean admin;

  public UserDTO(DefaultUser user) {
    this.id = user.getId();
    this.name = user.getName();
    this.admin = user.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
  }
}