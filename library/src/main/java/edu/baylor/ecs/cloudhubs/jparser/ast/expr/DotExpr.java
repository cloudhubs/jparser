package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DotExpr extends Expr {
    private Expr expr;
    private Expr selected;
}
