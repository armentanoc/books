package br.ucsal.dto;

public class LivroNotFoundResponse {

    private String message;
    private Long livroId;

    public LivroNotFoundResponse(Long livroId) {
        this.livroId = livroId;
        this.message = "Livro não encontrado.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long locationId) {
        this.livroId = locationId;
    }

}