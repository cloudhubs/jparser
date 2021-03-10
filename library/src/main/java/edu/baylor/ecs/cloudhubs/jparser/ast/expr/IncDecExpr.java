package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IncDecExpr extends Expr {
    @JsonProperty("is_pre")
    private boolean isPre;
    @JsonProperty("is_inc")
    private boolean isInc;
    private Expr expr;
}
