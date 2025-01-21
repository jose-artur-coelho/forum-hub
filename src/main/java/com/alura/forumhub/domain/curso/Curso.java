package com.alura.forumhub.domain.curso;

import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.dto.curso.CriarCursoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "cursos")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Topico> topicos;

    public Curso(CriarCursoDTO dto) {
        this.titulo = dto.titulo();
    }
}
