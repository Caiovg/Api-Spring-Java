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

import com.caio.os.domain.Tecnico;
import com.caio.os.dtos.TecnicoDTO;
import com.caio.os.services.TecnicoService;

/* O @CrossOrigin("*") habilta nossa api a receber requesições de varias fontes*/
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

	@Autowired
	private TecnicoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		Tecnico obj = service.findById(id);
		TecnicoDTO objDTO = new TecnicoDTO(obj);
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> list = service.findAll();
		List<TecnicoDTO> listDTO = new ArrayList<>();
		for(Tecnico obj : list) {
			listDTO.add(new TecnicoDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * Atualiza um Tecnico
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	/*
	 * Deleta Tecnico
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}	
}
