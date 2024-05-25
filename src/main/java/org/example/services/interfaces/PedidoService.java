package org.example.services.interfaces;

import org.example.entities.Pedido;

import java.util.List;

public interface PedidoService {

    void savePedido(Pedido pedido);

    List<Pedido> listarPedidosPendentesPorCentro(Long centroId);

}
