package com.alura.forumhub.domain.topico;


import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.resposta.Resposta;
import com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "topicos")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    private Usuario autor;

    @OneToMany(mappedBy = "topico")
    private Set<Resposta> respostas;

    @ManyToOne
    private Curso curso;


}
