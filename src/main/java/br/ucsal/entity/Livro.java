package br.ucsal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Livro extends BaseEntity {

		@Column(unique = true, nullable = false)
		private String titulo;

		@Column(nullable = false)
		private Integer anoPublicacao;

		@Column(nullable = false)
		private Categoria categoria;

		protected Livro() {
			// default for JPA
		}

		public Livro(String titulo, Integer anoPublicacao, Categoria categoria) {
			this.titulo = titulo;
			this.anoPublicacao = anoPublicacao;
			this.categoria = categoria;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Integer getAnoPublicacao() {
			return anoPublicacao;
		}

		public void setAnoPublicacao(Integer anoPublicacao) {
			this.anoPublicacao = anoPublicacao;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}
}
