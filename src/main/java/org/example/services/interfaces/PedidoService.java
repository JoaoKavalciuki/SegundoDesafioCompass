package org.example.services.interfaces;

import org.example.entities.Pedido;
import org.example.entities.enums.StatusPedido;

import java.util.List;

public interface PedidoService {

    void savePedido(Pedido pedido);

    List<Pedido> listarPedidosPendentesPorCentro(Long centroId);

}
