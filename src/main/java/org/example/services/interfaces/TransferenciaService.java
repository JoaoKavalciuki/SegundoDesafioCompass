package org.example.services.interfaces;

import org.example.entities.Abrigo;
import org.example.entities.CentroDistribuicao;
import org.example.entities.Item;
import org.example.entities.Transferencia;

public interface TransferenciaService {
    void transferirEntreCentros(Transferencia transferencia);
    void devolverParaAbrigo(CentroDistribuicao centro, Abrigo abrigo, Item item, int quantidade);
}
