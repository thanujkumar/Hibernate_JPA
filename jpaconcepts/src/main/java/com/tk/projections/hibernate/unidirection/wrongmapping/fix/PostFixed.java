package com.tk.projections.hibernate.unidirection.wrongmapping.fix;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

//This would create a joining table which looks like many to many
@Entity
@Data
@Table(name = "POSTS_FIXED")
public class PostFixed extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="post_id", nullable = false) //parent to child
    private List<CommentFixed> comments = new ArrayList<>();
}
