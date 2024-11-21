package br.ucsal.repository;

import br.ucsal.entity.Livro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	List<Livro> findAllByOrderByIdAsc();
}
