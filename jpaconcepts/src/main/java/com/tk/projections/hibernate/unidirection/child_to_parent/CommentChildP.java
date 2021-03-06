package com.tk.projections.hibernate.unidirection.child_to_parent;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "COMMENTS_CHILD2")
public class CommentChildP extends AuditModel {

    public CommentChildP() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    public CommentChildP(String _text) {
        text = _text;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private PostParentC post;

 }
