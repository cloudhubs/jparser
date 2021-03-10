package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Backup plan: we instantiate one */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherOp implements IOp {
    private String op;
}