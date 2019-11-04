package edu.baylor.ecs.ciljssa.builder;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.component.impl.MethodParam;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MethodInfoBuilder {

    private Component parentComponent;
    private String methodName;
    private String returnType;
    private String rawSource;
    private boolean staticMethod;
    private boolean abstractMethod;
    private AccessorType accessor;
    private List<MethodParam> methodParams;
    private List<AnnotationComponent> annotations; // Method annotations
    private List<MethodInfoComponent> subMethods;

    public MethodInfoComponent build() {
        MethodInfoComponent output = new MethodInfoComponent();
        output.setParent(this.parentComponent);
        output.setAccessor(this.accessor);
        output.setParent(this.parentComponent);
        output.setAnnotations(this.annotations);
        output.setMethodName(this.methodName);
        output.setMethodParams(this.methodParams);
        output.setSubMethods(this.subMethods);
        output.setReturnType(this.returnType);
        output.setRawSource(this.rawSource);
        output.setStaticMethod(this.staticMethod);
        output.setAbstractMethod(this.abstractMethod);
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

    public MethodInfoBuilder withAccessor(AccessorType acc) {
        this.accessor = acc;
        return this;
    }

    public MethodInfoBuilder asStaticMethod(boolean b) {
        this.staticMethod = b;
        return this;
    }

    public MethodInfoBuilder asAbstractMethod(boolean b) {
        this.abstractMethod = b;
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

    public MethodInfoBuilder withParentComponent(Component wrap) {
        this.parentComponent = wrap;
        return this;
    }

    public MethodInfoBuilder withRawSource(String source) {
        this.rawSource = source;
        return this;
    }

}
