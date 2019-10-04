package edu.baylor.ecs.ciljssa.builder;

import edu.baylor.ecs.ciljssa.model.MethodParam;
import edu.baylor.ecs.ciljssa.wrappers.AnnotationWrapper;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import edu.baylor.ecs.ciljssa.wrappers.MethodInfoWrapper;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MethodInfoBuilder {

    private IComponent parentComponent;
    private String methodName;
    private String returnType;
    private String accessor;
    private boolean staticMethod;
    private List<MethodParam> methodParams;
    private List<AnnotationWrapper> annotations; // Method annotations
    private List<MethodInfoWrapper> subMethods;

    public MethodInfoWrapper build() {
        MethodInfoWrapper output = new MethodInfoWrapper();
        output.setParentComponent(this.parentComponent);
        output.setAccessor(this.accessor);
        output.setParentComponent(this.parentComponent);
        output.setAnnotations(this.annotations);
        output.setMethodName(this.methodName);
        output.setMethodParams(this.methodParams);
        output.setSubMethods(this.subMethods);
        output.setReturnType(this.returnType);
        return output;
    }

    public MethodInfoBuilder withMethodName(String name) {
        this.methodName = name;
        return this;
    }

    public MethodInfoBuilder withReturnType(String type) {
        this.returnType = type;
        return this;
    }

    public MethodInfoBuilder withAccessor(String acc) {
        this.accessor = acc;
        return this;
    }

    public MethodInfoBuilder asStaticMethod(boolean b) {
        this.staticMethod = b;
        return this;
    }

    public MethodInfoBuilder withMethodParams(List<MethodParam> methodParams) {
        this.methodParams = methodParams;
        return this;
    }

    public MethodInfoBuilder withAnnotations(List<AnnotationWrapper> anno) {
        this.annotations = anno;
        return this;
    }

    public MethodInfoBuilder withSubMethods(List<MethodInfoWrapper> sub) {
        this.subMethods = sub;
        return this;
    }

    public MethodInfoBuilder withParentComponent(IComponent wrap) {
        this.parentComponent = wrap;
        return this;
    }

}
