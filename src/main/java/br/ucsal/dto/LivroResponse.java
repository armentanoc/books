package br.ucsal.dto;

import br.ucsal.entity.Categoria;

public record LivroResponse(Long id, String titulo, Integer anoPublicacao, Categoria categoria) {
}
