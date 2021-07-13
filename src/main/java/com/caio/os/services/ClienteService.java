package com.caio.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caio.os.domain.Cliente;
import com.caio.os.domain.Pessoa;
import com.caio.os.dtos.ClienteDTO;
import com.caio.os.repositories.ClienteRepository;
import com.caio.os.repositories.PessoaRepository;
import com.caio.os.services.exception.DataIntegratyViolationException;
import com.caio.os.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 * Busca pelo ID
	 */
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	/*
	 * Busca de todos os clientes
	 */
	public List<Cliente> findAll(){
		return repository.findAll();
	}

	/*
	 * Cria um novo Cliente
	 */
	public Cliente create(ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	/*
	 * Exceção para cpf, e busca para o cpf
	 */
	public Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}
}
