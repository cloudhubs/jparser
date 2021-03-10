package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.cloudhubs.jparser.ast.expr.Expr;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeclStmt extends Stmt {
    @JsonProperty("var_type")
    private String varType;
    private Expr rhs;
    @JsonProperty("is_static")
    private boolean isStatic;
    @JsonProperty("is_final")
    private boolean isFinal;
}
