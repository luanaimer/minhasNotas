package br.edu.ifsc.minhasnotas;

import android.graphics.Bitmap;

import java.util.Date;

public class Nota {
    private String texto;
    private String dataCriacao;
    private String dataAlteracao;
    private int id;
    private Bitmap image;

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setDataCriacao(String dataCriacao) {

        this.dataCriacao = dataCriacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
