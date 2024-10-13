package com.walsousa.gof.controller;

import com.walsousa.gof.exception.BusinessException;
import com.walsousa.gof.model.Cliente;
import com.walsousa.gof.model.SimplePageable;
import com.walsousa.gof.model.dto.ClientsDTO;
import com.walsousa.gof.model.dto.ClientsResponseDTO;
import com.walsousa.gof.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteRestControllerTest {

    @InjectMocks private ClienteRestController controller;

    @Mock private ClienteService clienteService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controller = new ClienteRestController(clienteService);
    }

    @Test
    void test() throws BusinessException {
        ClientsResponseDTO responseDTO = new ClientsResponseDTO();
        responseDTO.setPageable(new SimplePageable());
        List<ClientsDTO> clientList= new ArrayList<>();
        ClientsDTO cl= new ClientsDTO();
        cl.setName("joao");
        cl.setId("1");
        clientList.add(cl);

        responseDTO.setContent(clientList);

        when(clienteService.buscarTodos()).thenReturn(responseDTO);

        var result = controller.getAllClients();
        Assertions.assertNotNull(result);
    }

    @Test
    void testCliet() throws BusinessException {
        Cliente cliente = new Cliente();
        cliente.setNome("wal");
        when(clienteService.buscarPorId(any())).thenReturn(cliente);
        var result = controller.buscarPorId(1L);
        Assertions.assertNotNull(result);
    }
}
