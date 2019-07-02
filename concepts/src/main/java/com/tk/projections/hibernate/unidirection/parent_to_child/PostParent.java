package com.tk.projections.hibernate.unidirection.parent_to_child;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "POSTS_PARENT")
public class PostParent extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    /**
     * @JoinColumn indicates the entity is the owner of the relationship and the corresponding table has a column with
     * a foreign key to the referenced table. mappedBy indicates the entity is the inverse of the relationship.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="post_id", nullable = false) //parent to child
    private List<CommentChild> comments = new ArrayList<>();
}
