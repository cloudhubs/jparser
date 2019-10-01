package edu.baylor.ecs.ciljssa.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnnotationWrapper {

    @JsonIgnore
    private AnnotationExpr annotation;

    @JsonProperty(value = "name")
    private String asString;

    private String annotationMetaModel;
    private String metaModelFieldName;
    private String packageName;
    private String allowedRoles; // Only valid if is @AllowedRoles or similar annotation.

    public void setAsString(String inp) {
        this.asString = (inp.startsWith("@") ? "" : "@") + inp;
    }

    /**
     * Determine the parameter type (single, multi, none) for a given annotation.
     * @return "none" for MarkerAnnotationExpr, "single" for SingleMemberAnnotationExpr, "multi" for NormalAnnotationExpr
     */
    @JsonProperty(value = "param_type")
    public String annotationParamType() {
        if (annotation instanceof MarkerAnnotationExpr) {
            return "none";
        } else if (annotation instanceof SingleMemberAnnotationExpr) {
            return "single";
        } else if (annotation instanceof NormalAnnotationExpr) {
            return "multi";
        } else {
            return "unknown";
        }
    }

}