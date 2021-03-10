package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import edu.baylor.ecs.cloudhubs.jparser.ast.Block;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CatchStmt extends Stmt {
    private DeclStmt exc;
    private Block body;
}
