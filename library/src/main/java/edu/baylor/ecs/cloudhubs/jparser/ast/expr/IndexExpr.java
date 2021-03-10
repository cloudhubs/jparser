package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IndexExpr extends Expr {
    private Expr expr;
    @JsonProperty("index_expr")
    private Expr indexExpr;
}
