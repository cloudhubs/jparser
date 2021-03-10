package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import edu.baylor.ecs.cloudhubs.jparser.ast.Block;
import edu.baylor.ecs.cloudhubs.jparser.ast.stmt.DeclStmt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LambdaExpr extends Expr {
    private List<DeclStmt> parameters;
    private Block body;
}
