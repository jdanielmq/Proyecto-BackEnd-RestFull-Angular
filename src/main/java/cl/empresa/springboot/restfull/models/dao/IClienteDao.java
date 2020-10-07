package cl.empresa.springboot.restfull.models.dao;

import org.springframework.data.repository.CrudRepository;

import cl.empresa.springboot.restfull.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	


}
