package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.cloudhubs.jparser.ast.expr.Ident;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
public class VarDecl {
    @JsonProperty("var_type")
    private String varType;
    private Ident ident;
    @JsonProperty("is_static")
    private boolean isStatic;
    @JsonProperty("is_final")
    private boolean isFinal;
}
