package org.example.services;

import org.example.entities.Pedido;
import org.example.repositories.PedidoRepository;
import org.example.services.interfaces.PedidoService;

import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void savePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidosPendentesPorCentro(Long centroId) {
        return pedidoRepository.listarPedidosPendentesPorCentro(centroId);
    }

}
