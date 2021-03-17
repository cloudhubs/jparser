package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AssignExpr extends Expr {
    private List<Expr> lhs;
    private List<Expr> rhs;
}
