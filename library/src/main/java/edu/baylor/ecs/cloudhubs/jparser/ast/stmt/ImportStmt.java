package edu.baylor.ecs.cloudhubs.jparser.ast.stmt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ImportStmt extends Stmt {
    // Whether the import is a specific type or a package/module, etc
    private boolean container;

    // Whether the import lets functions, etc by referenced by name directly
    @JsonProperty("use_direct")
    private boolean useDirect;

    private String value;
}
