package com.tk.projections.hibernate.unidirection.wrongmapping.fix;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//This would create a joining table which looks like many to many
@Entity
@Data
@Table(name = "COMMENTS_FIXED")
public class CommentFixed extends AuditModel {

    public CommentFixed() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    public CommentFixed(String _text) {
        text = _text;
    }


}
