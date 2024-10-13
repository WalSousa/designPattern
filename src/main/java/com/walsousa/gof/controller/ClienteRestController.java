package com.walsousa.gof.controller;

import com.walsousa.gof.exception.BusinessException;
import com.walsousa.gof.exception.CepException;
import com.walsousa.gof.model.Cliente;
import com.walsousa.gof.model.dto.ClientsDTO;
import com.walsousa.gof.model.dto.ClientsResponseDTO;
import com.walsousa.gof.model.dto.CreateClientDTO;
import com.walsousa.gof.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 *
 */
@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<ClientsResponseDTO> getAllClients() throws BusinessException {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) throws BusinessException {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<ClientsDTO> insert(@RequestBody CreateClientDTO client) throws BusinessException {
		var result = clienteService.insert(client);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientsDTO> update(@PathVariable Long id, @RequestBody CreateClientDTO client) throws BusinessException, CepException {
		return ResponseEntity.ok(clienteService.update(id, client));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) throws BusinessException {
		clienteService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
