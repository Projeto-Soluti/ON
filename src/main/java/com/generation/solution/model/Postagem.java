package com.generation.solution.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tb_postagens")
public class Postagem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
	
    @NotBlank(message = "O Atributo título é Obrigatório!") 
    @Size(min = 5, max = 100, message = "O Atributo título deve conter no mínimo 05 e no máximo 100 caracteres")
    private String titulo;
	
    @NotBlank(message = "O Atributo texto é Obrigatório!")
    @Size(min = 10, max = 1000, message = "O Atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres")
    private String texto;
	
    @UpdateTimestamp
    private LocalDateTime data;

    private String foto;
	
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;

  
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Tema getTema() {
        return this.tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
