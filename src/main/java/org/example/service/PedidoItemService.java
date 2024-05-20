package org.example.service;

import org.example.entity.PedidoItem;
import org.example.repository.PedidoItemRepository;

public class PedidoItemService {
    private PedidoItemRepository pedidoItemRepository;

    public PedidoItemService(PedidoItemRepository pedidoItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public void savePedidoItem(PedidoItem pedidoItem){
        pedidoItemRepository.save(pedidoItem);
    }
}
