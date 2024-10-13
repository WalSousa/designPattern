package com.walsousa.gof.service.impl;

import com.walsousa.gof.exception.BusinessException;
import com.walsousa.gof.exception.CepException;
import com.walsousa.gof.model.Cliente;
import com.walsousa.gof.repository.ClienteRepository;
import com.walsousa.gof.model.Endereco;
import com.walsousa.gof.repository.EnderecoRepository;
import com.walsousa.gof.model.dto.ClientsDTO;
import com.walsousa.gof.model.dto.ClientsResponseDTO;
import com.walsousa.gof.model.dto.CreateClientDTO;
import com.walsousa.gof.model.mapper.GetAllClientesResponseMapper;
import com.walsousa.gof.service.ClienteService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public ClienteServiceImpl(@Value("${url.viaCep}") String url, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, RestTemplate restTemplate) {
        this.url = url;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ClientsResponseDTO buscarTodos() throws BusinessException {
        // Buscar todos os Clientes e faz o filtro para retornar apenas dados necessários p apresentação em lista.

        try {
            Iterable<Cliente> clientes = clienteRepository.findAll();
            if (IterableUtils.isEmpty(clientes))
                throw new BusinessException("Sem dados na base");
            return new GetAllClientesResponseMapper(clientes).getResponse();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Cliente buscarPorId(Long id) throws BusinessException {
        // Buscar Cliente por ID.

        try {
            Optional<Cliente> cliente = Optional.ofNullable(clienteRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Id não Encontrado")));
            Cliente client = new Cliente();
            if (cliente.isPresent()) {
                client = cliente.get();
            }
            return client;
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage(), e.getLocalizedMessage(), "", e);
        }
    }

    @Override
    public ClientsDTO insert(CreateClientDTO client) throws BusinessException {
        try {

            Cliente cliente = new Cliente();
            cliente.setNome(client.getName());
            cliente.setProfissao(client.getProfession());
            cliente.setIdade(client.getAge());
            Endereco endereco = new Endereco();
            endereco.setCep(client.getZipCode());
            cliente.setEndereco(endereco);

            salvarClienteComCep(cliente);

            ClientsDTO result = new ClientsDTO();
            result.setName(client.getName());
            return result;
        } catch (CepException e) {
            throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getDetails(), e);
        }
    }

    @Override
    public ClientsDTO update(Long id, CreateClientDTO client) throws CepException {
        // Buscar Cliente por ID, caso exista:
        ClientsDTO clientsResponse = new ClientsDTO();

        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if (clienteBd.isPresent()) {

            Cliente newClient = new Cliente();
            newClient.setId(id);
            newClient.setNome(client.getName());
            newClient.setProfissao(client.getProfession());
            newClient.setIdade(client.getAge());
            Endereco end = new Endereco();
            end.setCep(client.getZipCode());
            newClient.setEndereco(end);

            salvarClienteComCep(newClient);
        }

        clientsResponse.setId(String.valueOf(id));
        clientsResponse.setName(client.getName());
        return clientsResponse;
    }

    @Override
    public void deletar(Long id) throws BusinessException {
        // Deletar Cliente por ID.
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("Ocorreu um erro ao deletar", e);
        }
    }

    private void salvarClienteComCep(Cliente cliente) throws CepException {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        cep = cep.replace("-", "");
        if (8 != cep.length()) {
            throw new CepException("400", "CEP inválido", "CEP");
        }
        String finalCep = cep;
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            String urlViaCep = url.concat(finalCep).concat("/json/");
            Endereco newAddress = restTemplate.getForEntity(urlViaCep, Endereco.class).getBody();
            assert newAddress != null;
            enderecoRepository.save(newAddress);
            return newAddress;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
