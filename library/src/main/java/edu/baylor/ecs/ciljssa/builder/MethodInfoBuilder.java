package edu.baylor.ecs.ciljssa.builder;

import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MethodInfoBuilder {

    private IContainerComponent parentComponent;
    private String methodName;
    private String returnType;
    private String accessor;
    private boolean staticMethod;
    private List<MethodParam> methodParams;
    private List<AnnotationComponent> annotations; // Method annotations
    private List<MethodInfoComponent> subMethods;

    public MethodInfoComponent build() {
        MethodInfoComponent output = new MethodInfoComponent();
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

    public MethodInfoBuilder withAnnotations(List<AnnotationComponent> anno) {
        this.annotations = anno;
        return this;
    }

    public MethodInfoBuilder withSubMethods(List<MethodInfoComponent> sub) {
        this.subMethods = sub;
        return this;
    }

    public MethodInfoBuilder withParentComponent(IContainerComponent wrap) {
        this.parentComponent = wrap;
        return this;
    }

}
