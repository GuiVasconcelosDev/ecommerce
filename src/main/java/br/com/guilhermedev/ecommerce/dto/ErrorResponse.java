package br.com.guilhermedev.ecommerce.dto;

import java.time.Instant;

public class ErrorResponse {

    private Instant timestamp;
    private Integer status;
    private String erro;
    private String caminho;

    public ErrorResponse(Integer status, String erro, String caminho) {
        this.timestamp = Instant.now();
        this.status = status;
        this.erro = erro;
        this.caminho = caminho;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getCaminho() {
        return caminho;
    }
}
