package edu.baylor.ecs.cloudhubs.jparser.ast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.baylor.ecs.cloudhubs.jparser.ast.expr.*;
import edu.baylor.ecs.cloudhubs.jparser.ast.stmt.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Block.class, name="block"),
    @JsonSubTypes.Type(value = AssignExpr.class, name="assign_expr"),
    @JsonSubTypes.Type(value = BinaryExpr.class, name="binary_expr"),
    @JsonSubTypes.Type(value = CallExpr.class, name="call_expr"),
    @JsonSubTypes.Type(value = DotExpr.class, name="dot_expr"),
    @JsonSubTypes.Type(value = Ident.class, name="ident_expr"),
    @JsonSubTypes.Type(value = IncDecExpr.class, name="inc_dec_expr"),
    @JsonSubTypes.Type(value = IndexExpr.class, name="index_expr"),
    @JsonSubTypes.Type(value = InitListExpr.class, name="init_list_expr"),
    @JsonSubTypes.Type(value = LambdaExpr.class, name="lambda_expr"),
    @JsonSubTypes.Type(value = LogExpr.class, name="log_expr"),
    @JsonSubTypes.Type(value = ParenExpr.class, name="paren_expr"),
    @JsonSubTypes.Type(value = UnaryExpr.class, name="unary_expr"),
    @JsonSubTypes.Type(value = Literal.class, name="literal_expr"),

    @JsonSubTypes.Type(value = AssignStmt.class, name="assign_stmt"),
    @JsonSubTypes.Type(value = BreakStmt.class, name="break_stmt"),
    @JsonSubTypes.Type(value = CaseStmt.class, name="case_stmt"),
    @JsonSubTypes.Type(value = CatchStmt.class, name="catch_stmt"),
    @JsonSubTypes.Type(value = ContinueStmt.class, name="continue_stmt"),
    @JsonSubTypes.Type(value = DeclStmt.class, name="decl_stmt"),
    @JsonSubTypes.Type(value = DoWhileStmt.class, name="do_while_stmt"),
    @JsonSubTypes.Type(value = ExprStmt.class, name="expr_stmt"),
    @JsonSubTypes.Type(value = ForRangeStmt.class, name="for_range_stmt"),
    @JsonSubTypes.Type(value = ForStmt.class, name="for_stmt"),
    @JsonSubTypes.Type(value = IfStmt.class, name="if_stmt"),
    @JsonSubTypes.Type(value = ImportStmt.class, name="import_stmt"),
    @JsonSubTypes.Type(value = ReturnStmt.class, name="return_stmt"),
    @JsonSubTypes.Type(value = SwitchStmt.class, name="switch_stmt"),
    @JsonSubTypes.Type(value = ThrowStmt.class, name="throw_stmt"),
    @JsonSubTypes.Type(value = TryCatchStmt.class, name="try_catch_stmt"),
    @JsonSubTypes.Type(value = WhileStmt.class, name="while_stmt"),
})
public abstract class Node {
//    @Nullable
//    private String type;
}
