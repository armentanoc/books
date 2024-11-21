package br.ucsal.dto;

import br.ucsal.entity.Categoria;

public record LivroRequest(String titulo, Integer anoPublicacao, Categoria categoria) {
}
