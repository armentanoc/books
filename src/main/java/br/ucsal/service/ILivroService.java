package br.ucsal.service;

import java.util.List;
import java.util.Optional;

import br.ucsal.dto.*;

public interface ILivroService {
	
	AddLivroResponse add(LivroRequest request);

	Optional<LivroResponse> get(Long livroId);

	List<LivroResponse> getAll();

	DeleteResponse delete(Long livroId);

	UpdateResponse update(Long livroId, LivroRequest request);
}
