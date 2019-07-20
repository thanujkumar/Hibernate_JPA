package com.tk.projections.hibernate.unidirection.parent_to_child;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@Table(name = "COMMENTS_CHILD")
public class CommentChild extends AuditModel {

    public CommentChild() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    public CommentChild(String _text) {
        text = _text;
    }

}
