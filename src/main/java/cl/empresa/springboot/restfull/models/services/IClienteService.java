package cl.empresa.springboot.restfull.models.services;

import java.util.List;

import cl.empresa.springboot.restfull.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);

}
