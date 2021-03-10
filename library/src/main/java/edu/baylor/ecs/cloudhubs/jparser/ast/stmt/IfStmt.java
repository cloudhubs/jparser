package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.cloudhubs.jparser.ast.Block;
import edu.baylor.ecs.cloudhubs.jparser.ast.expr.Expr;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IfStmt extends Stmt {
    private Expr cond;
    private Block body;
    @JsonProperty("else_body")
    private Block elseBody;
}
