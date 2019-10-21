package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnnotationComponent extends Component {

    @JsonIgnore
    private AnnotationExpr annotation;
    @JsonIgnore
    private InstanceType instanceType = InstanceType.ANNOTATIONCOMPONENT;
    @JsonIgnore
    private Component parentComponent;

    @JsonProperty(value = "name")
    private String asString;

    private String annotationMetaModel;
    private String metaModelFieldName;
    private String packageName;
    private String allowedRoles; // Only valid if is @AllowedRoles or similar annotation.
    private String pathToComponent;
    private String instanceName;

    public void setAsString(String inp) {
        this.asString = (inp.startsWith("@") ? "" : "@") + inp;
    }

    public String getAsString() {
        return this.asString;
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



