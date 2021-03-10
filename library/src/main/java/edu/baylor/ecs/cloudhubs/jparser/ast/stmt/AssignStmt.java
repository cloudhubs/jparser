package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import edu.baylor.ecs.cloudhubs.jparser.ast.expr.Expr;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AssignStmt extends Stmt {
    private Expr lhs;
    private Expr rhs;
}
