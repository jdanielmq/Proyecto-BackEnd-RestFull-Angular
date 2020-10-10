package cl.empresa.springboot.restfull.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = null;
		try {
			cliente = IclienteService.findById(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(cliente==null) {
			response.put("mensaje", "El cliente id: ".concat(String.valueOf(id).concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Validated @RequestBody Cliente cliente, BindingResult  result) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteResponse = null;
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
								   .stream()
					               .map(error -> "El campo ".concat(error.getField()).concat(" ==> ").concat(error.getDefaultMessage()))
					               .collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			clienteResponse = IclienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(clienteResponse == null) {
			response.put("mensaje", "El cliente: ".concat(cliente.getNombre()).concat(" no se puedo insertar en la  base de datos."));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);			
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteResponse);
		
		return new ResponseEntity<Map<String, Object>>(response ,HttpStatus.CREATED); 
	}
	
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Validated @RequestBody Cliente cliente, BindingResult  result, @PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
								   .stream()
					               .map(error -> "El campo ".concat(error.getField()).concat(" => ").concat(error.getDefaultMessage()))
					               .collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		Cliente clienteActual = IclienteService.findById(id);
		if(cliente==null) {
			response.put("mensaje", "El cliente id: ".concat(String.valueOf(id).concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		Cliente clienteResponse = null;
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			
			clienteResponse = IclienteService.save(cliente);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido modificado con éxito!");
		response.put("cliente", clienteResponse);
		
		return new ResponseEntity<Map<String, Object>>(response ,HttpStatus.CREATED); 
	}
	
	@DeleteMapping("/clientes/{id}")

	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			IclienteService.delete(id);
			response.put("mensaje", "El cliente ha sido eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
