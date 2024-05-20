package org.example.service;


import org.example.entity.Pedido;
import org.example.repository.PedidoRepository;

public class PedidoService {
    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void savePedidoItem(Pedido pedidoItem){
        pedidoRepository.save(pedidoItem);
    }

    public Pedido findById(Long id){
        return pedidoRepository.findById(id);
    }
}
