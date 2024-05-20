package org.example.entity;

import jakarta.persistence.*;
import org.example.entity.pk.PedidoItemPk;

import java.util.Objects;

@Entity
@Table(name = "pedido_itens")
public class PedidoItem {

   @EmbeddedId
   private PedidoItemPk id = new PedidoItemPk();

    private Integer quantidade;

    public PedidoItem() {
    }


    public PedidoItem(Pedido pedido, Item item, Integer quantidade){
        this.quantidade = quantidade;
        this.id.setPedido(pedido);
        this.id.setItem(item);
    }

    public PedidoItemPk getId() {
        return id;
    }

    public void setId(PedidoItemPk id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItem that = (PedidoItem) o;
        return Objects.equals(id, that.id);
    }

}
