package br.com.school.alert.controller;


import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.school.alert.controller.dto.CityDTO;
import br.com.school.alert.domain.Escola;
import br.com.school.alert.exception.NotFoundException;
import br.com.school.alert.exception.WebException;
import br.com.school.alert.repository.CityRepository;
import br.com.school.alert.repository.HotelRepository;
import br.com.school.alert.security.Roles;
import br.com.school.alert.utils.MapperUtils;
import br.com.school.alert.utils.SQLLikeUtils;

@RestController
@RequestMapping("/escolaController")
@Secured(Roles.ROLE_ADMIN)
public class EscolaController {

  private MapperUtils<Escola, CityDTO> convert = new MapperUtils<Escola, CityDTO>(Escola.class, CityDTO.class);

  @Autowired
  private CityRepository repository;

  @Autowired
  private HotelRepository hotelRepository;

  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET)
  public Page<CityDTO> list(@PageableDefault(page = 0, size = 50, sort = "name") Pageable page,
      @RequestParam(value = "search", required = false) String search) {

    Page<Escola> result;

    if (StringUtils.hasText(search)) {
      result = repository.search(SQLLikeUtils.like(search), page);
    } else {
      result = repository.findAll(page);
    }

    return new PageImpl<CityDTO>(result.getContent().stream().map(c -> convert.toDTO(c)).collect(Collectors.toList()),
        page, result.getTotalElements());
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public CityDTO read(@PathVariable("id") Long id) {

    Escola city = repository.findOne(id);
    if (city == null) {
      throw new NotFoundException(Escola.class);
    }
    return convert.toDTO(city);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CityDTO create(@Valid @RequestBody CityDTO dto) {

    Escola entity = convert.toEntity(dto);
    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @RequestMapping(value = "/{ref}", method = RequestMethod.PUT)
  @ResponseBody
  public CityDTO update(@PathVariable("ref") Long ref, @Valid @RequestBody CityDTO dto) {
    Escola entity = repository.findOne(ref);
    if (entity == null) {
      throw new NotFoundException(Escola.class);
    }
    convert.updateEntity(entity, dto, "id", "natures");
    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public CityDTO delete(@PathVariable("id") Long id) {

    Escola city = repository.findOne(id);

    if (city == null) {
      throw new NotFoundException(Escola.class);
    }
    if (hotelRepository.countByCity(city) > 0) {
      throw new WebException(HttpStatus.PRECONDITION_FAILED, "city.hasHotel");
    }

    this.repository.delete(city);

    return convert.toDTO(city);
  }

}
