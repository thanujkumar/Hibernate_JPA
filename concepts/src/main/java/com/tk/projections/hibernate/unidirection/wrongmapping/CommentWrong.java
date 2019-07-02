package com.tk.projections.hibernate.unidirection.wrongmapping;

import com.tk.projections.hibernate.AuditModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//This would create a joining table which looks like many to many
@Entity
@Data
@Table(name = "COMMENTS_WRONG")
public class CommentWrong extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    public CommentWrong(String _text) {
        text = _text;
    }

}
