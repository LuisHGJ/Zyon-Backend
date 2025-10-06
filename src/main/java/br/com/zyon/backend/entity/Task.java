package br.com.zyon.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioID;
    private Long cicloTempo;
    private String nome;
    private String descricao;
    private String prioridade;
    private String dificuldade;
    private Short energia;
    private Short xp;
    private Boolean realizado;

    public Long getId() {
        return id;
    };

    public void setId(Long id) {
        this.id = id;
    };

    public Long getUsuarioID() {
        return usuarioID;
    };

    public void setUsuarioId(Long usuarioID) {
        this.usuarioID = usuarioID;
    };

    public Long getCicloTempo() {
        return cicloTempo;
    };

    public void setCicloTempo(Long cicloTempo) {
        this.cicloTempo = cicloTempo;
    };

    public String getNome() {
        return nome;
    };

    public void setNome(String nome) {
        this.nome = nome;
    };

    public String getDescricao() {
        return descricao;
    };

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    };

    public String getPrioridade() {
        return prioridade;
    };

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    };

    public String getDificuldade() {
        return dificuldade;
    };

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    };

    public Short getEnergia() {
        return energia;
    };

    public void setEnergia(Short energia) {
        this.energia = energia;
    };

    public Short getXP() {
        return xp;
    };

    public void setXP(Short xp) {
        this.xp = xp;
    };

    public Boolean getRealizado() {
        return realizado;
    };

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    };
};