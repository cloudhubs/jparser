package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CallExpr extends Expr {
    private Expr name;
    private List<Expr> args;
}
