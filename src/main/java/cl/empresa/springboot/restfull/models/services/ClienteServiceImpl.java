package cl.empresa.springboot.restfull.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.empresa.springboot.restfull.models.dao.IClienteDao;
import cl.empresa.springboot.restfull.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao IClienteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) IClienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return IClienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return IClienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		IClienteDao.deleteById(id);
	}

}
