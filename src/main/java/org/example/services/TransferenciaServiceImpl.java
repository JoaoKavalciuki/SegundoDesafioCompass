package org.example.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.example.entities.CentroDistribuicao;
import org.example.entities.EstoqueAbrigo;
import org.example.entities.EstoqueCentro;
import org.example.entities.Item;
import org.example.entities.Transferencia;
import org.example.entities.enums.TipoTransferencia;
import org.example.repositories.AbrigoRepository;
import org.example.repositories.CentroDistribuicaoRepository;
import org.example.repositories.EstoqueAbrigoRepository;
import org.example.repositories.EstoqueCentroRepository;
import org.example.repositories.ItemRepository;
import org.example.repositories.TransferenciaRepository;
import org.example.services.interfaces.TransferenciaService;

import jakarta.persistence.EntityManager;

public class TransferenciaServiceImpl implements TransferenciaService {

	private EntityManager em;
	private TransferenciaRepository transferenciaRepository;
	private EstoqueCentroRepository estoqueRepository;
	private EstoqueAbrigoRepository estoqueAbrigoRepository;
	private AbrigoRepository abrigoRepository;
	private CentroDistribuicaoRepository cdRepository;
	private ItemRepository itemRepository;
	
	public TransferenciaServiceImpl(EntityManager em) {
		this.em = em;
		this.transferenciaRepository = new TransferenciaRepository(em);
		this.estoqueRepository = new EstoqueCentroRepository();
		this.abrigoRepository = new AbrigoRepository(em);
		this.cdRepository = new CentroDistribuicaoRepository();
		this.itemRepository = new ItemRepository();
		this.estoqueAbrigoRepository = new EstoqueAbrigoRepository();
	}

	@Override
	public void transferirEntreCentros(Transferencia transferencia) {
		transferencia.setTipoTransferencia(TipoTransferencia.CENTROPARACENTRO);
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
	        System.out.println("Estoque de Destino encontrado com quantidade: " + estoqueOrigem.get().getQuantidade());
	    } else {
	        System.out.println("Estoque de destino não encontrado.");
	    }
		estoqueDestino.ifPresentOrElse(existenteEstoque -> {
			int quantidade = existenteEstoque.getQuantidade();
			if (quantidade + transferencia.getQuantidade() > 1000) {
				System.out.println("Operação invalidada: A quantidade ultrapassa o limite do centro");
			} else {
				estoqueOrigem.ifPresentOrElse(estoqueOriginal -> {
					int quantidadeOrigem = estoqueOriginal.getQuantidade();
					if (quantidadeOrigem >= transferencia.getQuantidade()) {
						if (quantidadeOrigem != transferencia.getQuantidade())
							estoqueRepository.updateEstoque(estoqueOriginal.getCentroDistribuicao().getId(), 
								estoqueOriginal.getItem().getId(), transferencia.getQuantidade());
						else
							estoqueRepository.deleteEstoque(estoqueOriginal.getCentroDistribuicao().getId(), 
								estoqueOriginal.getItem().getId());
							
						existenteEstoque
								.setQuantidade(existenteEstoque.getQuantidade() + transferencia.getQuantidade());
						estoqueRepository.save(existenteEstoque);
						
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
					if (quantidadeOrigem != transferencia.getQuantidade())
						estoqueRepository.updateEstoque(estoqueOriginal.getCentroDistribuicao().getId(), 
							estoqueOriginal.getItem().getId(), transferencia.getQuantidade());
					else
						estoqueRepository.deleteEstoque(estoqueOriginal.getCentroDistribuicao().getId(), 
							estoqueOriginal.getItem().getId());
					
					System.out.println("Criando novo estoque");
					EstoqueCentro novoEstoque = new EstoqueCentro();
					novoEstoque.setCentroDistribuicao(destino);
					novoEstoque.setItem(transferencia.getItem());
					novoEstoque.setQuantidade(transferencia.getQuantidade());
					estoqueRepository.save(novoEstoque);
					System.out.println("Novo estoque criado com sucesso!");
					
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
	public void devolverItem(Long centroId, Long abrigoId, Long itemId, int quantidade) {
		System.out.println("A quantidade do pedido ultrapassa o limite do abrigo, devolvendo itens de acordo com o limite.");
		Item item = itemRepository.findById(itemId);
		Optional<EstoqueCentro> estoqueCentroOpt = estoqueRepository.findByCDeItem(centroId, itemId);
		Optional<EstoqueAbrigo> estoqueAbrigoOpt = estoqueAbrigoRepository.findEstoqueByItemTipo(abrigoId, item.getItemTipo(), itemId);
		if (estoqueCentroOpt.isPresent()) {
			if (estoqueAbrigoOpt.isPresent()) {
				EstoqueCentro estoqueCentro = estoqueCentroOpt.get();
				EstoqueAbrigo estoqueAbrigo = estoqueAbrigoOpt.get();
				System.out.println("EstoqueAbrigo quantidade: "+estoqueAbrigo.getQuantidade());
				int aDevolver = 0;
				if (estoqueAbrigo.getQuantidade()<=200) {
					estoqueAbrigo.setQuantidade(estoqueAbrigo.getQuantidade()+quantidade);;
				}		
			
				aDevolver = estoqueAbrigo.getQuantidade()-200;
				
				System.out.println("A devolver: "+aDevolver);
				estoqueCentro.setQuantidade(estoqueCentro.getQuantidade()+aDevolver);
				System.out.println("Estoque centro atualizado "+estoqueCentro);
				estoqueRepository.save(estoqueCentro);;
				
				estoqueAbrigo.setQuantidade(estoqueAbrigo.getQuantidade()-aDevolver);
				estoqueAbrigoRepository.save(estoqueAbrigo);;
				registrar(centroId, abrigoId, itemId, aDevolver, TipoTransferencia.ABRIGOPARACENTRO);					
			}
		}
	}

	public void registrar(Long centroId, Long abrigoId, Long itemId, int quantidade, TipoTransferencia tipo) {
		Transferencia transferencia = new Transferencia();
		transferencia.setAbrigo(abrigoRepository.findById(abrigoId));
		if (tipo == TipoTransferencia.CENTROPARAABRIGO)
			transferencia.setOrigemCentro(cdRepository.findById(centroId));
		else
			transferencia.setDestinoCentro(cdRepository.findById(centroId));
		transferencia.setItem(itemRepository.findById(itemId));
		transferencia.setQuantidade(quantidade);
		transferencia.setTipo(itemRepository.findById(itemId).getItemTipo());
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		transferencia.setDataTransferencia(timestamp);
		transferencia.setTipoTransferencia(tipo);
		transferenciaRepository.save(transferencia);
	}

	@Override
	public void verificarDevolucao(Long abrigoId, Long centroId, Long itemId, int quantidade) {
		Item item = itemRepository.findById(itemId);
		Optional<EstoqueAbrigo> estoqueAbrigoOpt = estoqueAbrigoRepository.findEstoqueByItemTipo(abrigoId, item.getItemTipo(), itemId); 
		if (estoqueAbrigoOpt.isPresent()) {
			EstoqueAbrigo estoqueAbrigo = estoqueAbrigoOpt.get();			
			if (quantidade + estoqueAbrigo.getQuantidade() > 200) {
				System.out.println("verificarDevolucao: estoque abrigo quantidade: "+estoqueAbrigo.getQuantidade());
	        	devolverItem(centroId, abrigoId, itemId, quantidade);
			}
		}
	}

}
