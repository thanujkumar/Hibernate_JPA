package com.tk.projections.hibernate.unidirection.wrongmapping;

import com.tk.projections.hibernate.AuditModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

//This would create a joining table which looks like many to many
@Entity
@Data
@Table(name = "POSTS_WRONG")
public class PostWrong extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentWrong> comments = new ArrayList<>();
}
