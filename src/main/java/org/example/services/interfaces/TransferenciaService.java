package org.example.services.interfaces;

import org.example.entities.Transferencia;
import org.example.entities.enums.TipoTransferencia;

public interface TransferenciaService {
    void transferirEntreCentros(Transferencia transferencia);
	void devolverItem(Long centroId, Long abrigoId, Long itemId, int quantidade);
	void registrar(Long centroId, Long abrigoId, Long itemId, int quantidade, TipoTransferencia tipo);
	void verificarDevolucao(Long abrigoId, Long centroId, Long itemId,int quantidade);
}
