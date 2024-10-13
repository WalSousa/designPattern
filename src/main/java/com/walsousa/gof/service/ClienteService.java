package com.walsousa.gof.service;


import com.walsousa.gof.exception.BusinessException;
import com.walsousa.gof.exception.CepException;
import com.walsousa.gof.model.Cliente;
import com.walsousa.gof.model.dto.ClientsDTO;
import com.walsousa.gof.model.dto.ClientsResponseDTO;
import com.walsousa.gof.model.dto.CreateClientDTO;

public interface ClienteService {

	ClientsResponseDTO buscarTodos() throws BusinessException;

	Cliente buscarPorId(Long id) throws BusinessException;

	ClientsDTO insert(CreateClientDTO cliente) throws BusinessException;

	ClientsDTO update(Long id, CreateClientDTO cliente) throws BusinessException, CepException;

	void deletar(Long id) throws BusinessException;

}
