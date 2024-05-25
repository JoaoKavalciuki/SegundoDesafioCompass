package org.example.services;

import java.util.Optional;
import java.util.Scanner;

import org.example.entities.Abrigo;
import org.example.entities.CentroDistribuicao;
import org.example.entities.EstoqueCentro;
import org.example.entities.Item;
import org.example.entities.Transferencia;
import org.example.repositories.EstoqueCentroRepository;
import org.example.repositories.TransferenciaRepository;
import org.example.services.interfaces.TransferenciaService;

import jakarta.persistence.EntityManager;

public class TransferenciaServiceImpl implements TransferenciaService {

	private EntityManager em;
	private TransferenciaRepository transferenciaRepository;

	public TransferenciaServiceImpl(EntityManager em) {
		this.em = em;
		this.transferenciaRepository = new TransferenciaRepository(em);
	}

	@Override
	public void transferirEntreCentros(Transferencia transferencia) {
		EstoqueCentroRepository estoqueRepository = new EstoqueCentroRepository();
		Item item = transferencia.getItem();
		CentroDistribuicao origem = transferencia.getOrigemCentro();
		CentroDistribuicao destino = transferencia.getDestinoCentro();
		  System.out.println("Buscando estoque de origem...");
		Optional<EstoqueCentro> estoqueOrigem = estoqueRepository.findByCDeItem(origem.getId(), item.getId());
		if (estoqueOrigem.isPresent()) {
	        System.out.println("Estoque de origem encontrado com quantidade: " + estoqueOrigem.get().getQuantidade());
	    } else {
	        System.out.println("Estoque de origem não encontrado."); //exceção NullPointer because "origem" is null
	    }
		System.out.println("Buscando estoque de destino...");
		Optional<EstoqueCentro> estoqueDestino = estoqueRepository.findByCDeItem(destino.getId(), item.getId());
		if (estoqueDestino.isPresent()) {
	        System.out.println("Estoque de estoqueDestino encontrado com quantidade: " + estoqueOrigem.get().getQuantidade());
	    } else {
	        System.out.println("Estoque de destino não encontrado.");
	    }
		estoqueDestino.ifPresentOrElse(existenteEstoque -> {
			  System.out.println("estoque existente");
			int quantidade = existenteEstoque.getQuantidade();
			if (quantidade + transferencia.getQuantidade() > 1000) {
				System.out.println("Operação invalidada: A quantidade ultrapassa o limite do centro");
			} else {
				estoqueOrigem.ifPresentOrElse(estoqueOriginal -> {
					int quantidadeOrigem = estoqueOriginal.getQuantidade();

					if (quantidadeOrigem > transferencia.getQuantidade()) {
						estoqueOriginal.setQuantidade(quantidadeOrigem - transferencia.getQuantidade());
						estoqueRepository.update(estoqueOriginal);
						
						existenteEstoque
								.setQuantidade(existenteEstoque.getQuantidade() + transferencia.getQuantidade());
						estoqueRepository.update(existenteEstoque);
						
						transferenciaRepository.save(transferencia);
					} else {
						 System.out.println("Quantidade atual na origem: " + quantidadeOrigem);
					        System.out.println("Quantidade a ser transferida: " + transferencia.getQuantidade());
						System.out.println("Operação invalidada: Quantidade insuficiente no centro de origem");
					}
				}, () -> {
					System.out.println("Operação invalidada: Estoque de origem não encontrado");
				});
			}
		}, () -> {
			estoqueOrigem.ifPresentOrElse(estoqueOriginal -> {
				int quantidadeOrigem = estoqueOriginal.getQuantidade();
				if (quantidadeOrigem >= transferencia.getQuantidade()) {
					estoqueOriginal.setQuantidade(quantidadeOrigem - transferencia.getQuantidade());
					estoqueRepository.update(estoqueOriginal);
					System.out.println("Criando novo estoque");
					EstoqueCentro novoEstoque = new EstoqueCentro();
					novoEstoque.setCentroDistribuicao(destino);
					novoEstoque.setItem(transferencia.getItem());
					novoEstoque.setQuantidade(transferencia.getQuantidade());
					estoqueRepository.save(novoEstoque);
					System.out.println("novo estoque criado: "+novoEstoque.toString());
					transferenciaRepository.save(transferencia);
				} else {
					 System.out.println("Quantidade atual na origem: " + quantidadeOrigem);
				        System.out.println("Quantidade a ser transferida: " + transferencia.getQuantidade());
					System.out.println("Operação invalidada: Quantidade insuficiente no centro de origem");
				}
			}, () -> {
				System.out.println("Operação invalidada: Estoque de origem não encontrado");
			});
		});
	}

	@Override
	public void devolverParaAbrigo(CentroDistribuicao centro, Abrigo abrigo, Item item, int quantidade) {
		// TODO Auto-generated method stub

	}

}
