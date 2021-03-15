package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.cloudhubs.jparser.ast.Block;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TryCatchStmt extends Stmt {
    @JsonProperty("try_body")
    private Block tryBody;
    @JsonProperty("catch_bodies")
    private List<CatchStmt> catchBodies;
}
