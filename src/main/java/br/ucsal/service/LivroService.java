package br.ucsal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.dto.*;
import br.ucsal.entity.Livro;
import br.ucsal.repository.LivroRepository;

@Service
public class LivroService implements ILivroService {

	@Autowired
	private LivroRepository repository;
	
	@Override
	public AddLivroResponse add(LivroRequest request) {
		AddLivroResponse response = new AddLivroResponse(false, "Erro desconhecido", null);
		var livro = new Livro(request.titulo(), request.anoPublicacao(), request.categoria());
		var currentYear = LocalDate.now().getYear();
		
		if(request.anoPublicacao() > currentYear) {
			return new AddLivroResponse(false, "O ano de publicação ("+livro.getAnoPublicacao()+") não pode ser maior do que o ano atual ("+currentYear+").", null);
		}
		
		try {
			repository.save(livro);
			response = new AddLivroResponse(true, "Livro criado com sucesso", livro.getId());
		} catch (Exception ex) {
			if (ex.getMessage().contains("duplicar valor da chave") || ex.getMessage().contains("duplicated key")) {
				System.out.println(ex.getMessage());
				response = new AddLivroResponse(false, "Livro `" + request.titulo() + "` já existe.", livro.getId());
			}
		}
		return response;
	}

	@Override
	public Optional<LivroResponse> get(Long livroId) {
		return repository.findById(livroId).map(livro -> 
		new LivroResponse(livro.getId(), livro.getTitulo(), livro.getAnoPublicacao(), livro.getCategoria()));
	}

	@Override
	public List<LivroResponse> getAll() {
		var livros = repository.findAllByOrderByIdAsc();
		return livros.stream().map(livro -> new LivroResponse(livro.getId(), livro.getTitulo(), livro.getAnoPublicacao(), livro.getCategoria()))
				.collect(Collectors.toList());
	}

	@Override
	public DeleteResponse delete(Long livroId) {
		var response = new DeleteResponse(false, "Erro desconhecido.");
		try {
			var optionalLivro = repository.findById(livroId);

			if (optionalLivro.isEmpty())
				return new DeleteResponse(false, "Livro não encontrado.");

			var livro = optionalLivro.get();

			repository.delete(livro);
			
			return new DeleteResponse(true, "Livro excluído com sucesso.");
		}
		catch (Exception ex) {
			if (ex.getMessage().contains("restrição de chave estrangeira") || ex.getMessage().contains("foreign key")) {
				System.out.println(ex.getMessage());
				return new DeleteResponse(false, "O livro não pode ser excluído porque há uma solicitação associada.");
			}
		}
		return response;
	}

	@Override
	public UpdateResponse update(Long livroId, LivroRequest request) {
		
		var currentYear = LocalDate.now().getYear();
		
		if(request.anoPublicacao() > currentYear) {
			return new UpdateResponse(false, "O ano de publicação ("+request.anoPublicacao()+") não pode ser maior do que o ano atual ("+currentYear+").");
		}
		
		var response = new UpdateResponse(false, "Erro desconhecido");
		
		var optionalLivro = repository.findById(livroId);

		if (optionalLivro.isEmpty())
			return new UpdateResponse(false, "Livro não encontrado.");
		
		var livro = optionalLivro.get();
		
		try {
			livro.setTitulo(request.titulo());
			livro.setAnoPublicacao(request.anoPublicacao());
			livro.setCategoria(request.categoria());
			repository.save(livro);
			response = new UpdateResponse(true, "Livro atualizado com sucesso");
		} catch (Exception ex) {
			if (ex.getMessage().contains("duplicar valor da chave") || ex.getMessage().contains("duplicated key")) {
				System.out.println(ex.getMessage());
				response = new UpdateResponse(false, "Título `" + request.titulo() + "` já existe.");
			} else {
				response = new UpdateResponse(false, "Erro desconhecido: " + ex.getMessage());
			}
		}

		return response;
	}

}
