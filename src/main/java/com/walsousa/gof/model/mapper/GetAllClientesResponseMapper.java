package com.walsousa.gof.model.mapper;

import com.walsousa.gof.model.Cliente;
import com.walsousa.gof.model.dto.ClientsDTO;
import com.walsousa.gof.model.dto.ClientsResponseDTO;
import org.apache.commons.collections4.IterableUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetAllClientesResponseMapper {

  private final ClientsResponseDTO response = new ClientsResponseDTO();
  public GetAllClientesResponseMapper(Iterable<Cliente> clientes) {

    List<ClientsDTO> clientsDTOList = new ArrayList<>(IterableUtils.size(clientes));

    for (Cliente item : clientes) {
      ClientsDTO clientsDTO = new ClientsDTO();
      clientsDTO.setId(String.valueOf(item.getId()));
      clientsDTO.setName(item.getNome());
      clientsDTOList.add(clientsDTO);
    }

    response.setContent(clientsDTOList);
  }
}
