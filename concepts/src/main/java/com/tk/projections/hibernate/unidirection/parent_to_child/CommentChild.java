package com.tk.projections.hibernate.unidirection.parent_to_child;

import com.tk.projections.hibernate.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@NoArgsConstructor
@Table(name = "COMMENTS_CHILD")
public class CommentChild extends AuditModel {

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
