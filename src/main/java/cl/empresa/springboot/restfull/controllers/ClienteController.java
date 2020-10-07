package cl.empresa.springboot.restfull.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.empresa.springboot.restfull.models.entity.Cliente;
import cl.empresa.springboot.restfull.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private IClienteService IclienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return IclienteService.findAll();
	}

}
