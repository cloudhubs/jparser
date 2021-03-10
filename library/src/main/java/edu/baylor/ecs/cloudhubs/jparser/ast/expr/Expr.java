package edu.baylor.ecs.cloudhubs.jparser.ast.expr;

import edu.baylor.ecs.cloudhubs.jparser.ast.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class Expr extends Node {
}
