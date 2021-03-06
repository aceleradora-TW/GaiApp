package com.thoughtworks.aceleradora.dominio;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "estados")
@Access(AccessType.FIELD)
public class Estado {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String uf;

    @OneToMany(mappedBy = "estado")
    @OrderBy(value="nome")
    private List<Cidade> cidades;

    public Estado() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }



    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
