package br.ucs.mobile.model;

public class ConteudoLido {

    private Integer conteudoId;

    private String conteudoCodigoBarras;

    private String conteudoTipo;

    public Integer getConteudoId() {
        return conteudoId;
    }

    public void setConteudoId(Integer conteudoId) {
        this.conteudoId = conteudoId;
    }

    public String getConteudoCodigoBarras() {
        return conteudoCodigoBarras;
    }

    public void setConteudoCodigoBarras(String conteudoCodigoBarras) {
        this.conteudoCodigoBarras = conteudoCodigoBarras;
    }

    public String getConteudoTipo() {
        return conteudoTipo;
    }

    public void setConteudoTipo(String conteudoTipo) {
        this.conteudoTipo = conteudoTipo;
    }
}
