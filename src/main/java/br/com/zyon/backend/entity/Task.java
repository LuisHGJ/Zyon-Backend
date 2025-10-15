package br.com.zyon.backend.entity;

import java.sql.Date;

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
    private String nome;
    private String descricao;
    private String dificuldade;
    private Long cicloTempo;
    private Boolean concluido;
    private Short recompensaXp;
    private String prioridade;
    private Date dataAgendada;

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

    public Short getRecompensa_xp() {
        return recompensaXp;
    };

    public void setRecompensaXp(Short recompensaXp) {
        this.recompensaXp = recompensaXp;
    };

    public Boolean getConcluido() {
        return concluido;
    };

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    };

    public Date getDataAgendada() {
        return dataAgendada;
    };

    public void setDataAgendada(Date dataAgendada) {
        this.dataAgendada = dataAgendada;
    };
};