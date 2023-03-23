package lab.uel.prova1adminjoaokikuti.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "restaurante")
public class Restaurante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "restaurante")
    private List<ItemCardapio> itemCardapio;

    @Override
    public boolean equals(Object b) {
        if (!(b instanceof Restaurante)) {
            return false;
        }

        return this.hashCode() == b.hashCode();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
