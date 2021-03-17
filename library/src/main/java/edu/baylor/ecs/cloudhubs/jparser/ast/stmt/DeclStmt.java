package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import edu.baylor.ecs.cloudhubs.jparser.ast.expr.Expr;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeclStmt extends Stmt {
    private List<VarDecl> variables;
    private List<Expr> expressions;
}
