package com.caio.os.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.caio.os.domain.Cliente;
import com.caio.os.dtos.ClienteDTO;
import com.caio.os.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
		Cliente obj = service.findById(id);
		ClienteDTO objDTO = new ClienteDTO(obj);
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = new ArrayList<>();
		for(Cliente obj : list) {
			listDTO.add(new ClienteDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente newObj = service.create(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	                            .path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	   
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
		ClienteDTO newObj = new ClienteDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	 @DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
