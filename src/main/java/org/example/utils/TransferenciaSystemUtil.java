package org.example.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Item;
import org.example.entities.Transferencia;
import org.example.repositories.CentroDistribuicaoRepository;
import org.example.repositories.ItemRepository;
import org.example.services.TransferenciaServiceImpl;

public class TransferenciaSystemUtil {
    private TransferenciaServiceImpl transferenciaService;

    public TransferenciaSystemUtil(TransferenciaServiceImpl transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

	public void transferir(Scanner sc) {
		Transferencia transferencia = new Transferencia();
		transferencia.setAbrigo(null);
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		transferencia.setDataTransferencia(timestamp);
		
		System.out.println("Digite o item que deseja transferir: ");
		Long itemId = sc.nextLong();
		ItemRepository itemRepository = new ItemRepository();
		Item item = itemRepository.findById(itemId);
		transferencia.setItem(item);
		transferencia.setTipo(item.getItemTipo());//exceção NullPointer because "item" is null
		
		System.out.println("Digite o centro de origem: ");
		Long origemId = sc.nextLong();
		CentroDistribuicaoRepository centroRepository = new CentroDistribuicaoRepository();
		CentroDistribuicao origem = centroRepository.findById(origemId);
		transferencia.setOrigemCentro(origem);
		
		
		System.out.println("Digite o centro de destino: ");
		Long destinoId = sc.nextLong();
		CentroDistribuicao destino = centroRepository.findById(destinoId);
		transferencia.setDestinoCentro(destino);
		
		System.out.println("Digite a quantidade desejada: ");
		int quantidade = sc.nextInt();
		while (quantidade<=0||quantidade>1000) {
			System.out.println("Quantidade invalida: Digite uma quantia entre 1 e 1000");
			quantidade = sc.nextInt();
		}
		transferencia.setQuantidade(quantidade);		
		
		transferenciaService.transferirEntreCentros(transferencia);
		
	}

   
}
