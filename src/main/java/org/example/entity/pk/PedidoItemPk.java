package org.example.entity.pk;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.entity.Item;
import org.example.entity.Pedido;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PedidoItemPk implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public PedidoItemPk(){
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItemPk that = (PedidoItemPk) o;
        return Objects.equals(item, that.item) && Objects.equals(pedido, that.pedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, pedido);
    }
}
