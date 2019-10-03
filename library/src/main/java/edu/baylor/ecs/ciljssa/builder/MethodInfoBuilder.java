package edu.baylor.ecs.ciljssa.builder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import edu.baylor.ecs.ciljssa.wrappers.AnnotationWrapper;
import edu.baylor.ecs.ciljssa.wrappers.ComponentWrapper;
import edu.baylor.ecs.ciljssa.wrappers.MethodInfoWrapper;

import java.util.ArrayList;
import java.util.List;

public class MethodInfoBuilder {

    private MethodDeclaration methodDeclaration;
    private ComponentWrapper parentComponent;

    private String methodName;
    private String returnType;
    private String accessor;
    private boolean staticMethod;
    private List<MethodParam> methodParams;
    private List<AnnotationWrapper> annotations; // Method annotations
    private List<MethodInfoWrapper> subMethods;

    public MethodInfoBuilder(MethodDeclaration d, ComponentWrapper parent) {
        this.methodDeclaration = d;
        this.parentComponent = parent;
    }

    public MethodInfoWrapper build() {
        buildMissingProperties();
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

    private void buildMissingProperties() {
        this.staticMethod = this.methodDeclaration.isStatic();
        this.subMethods = this.subMethods == null ? generateSubmethods() : this.subMethods;
        this.annotations = this.annotations == null ? generateAnnotations() : this.annotations;
        this.methodParams = this.methodParams == null ? generateMethodParams() : this.methodParams;
        this.returnType = this.returnType == null ? this.methodDeclaration.getTypeAsString() : this.returnType;
        this.methodName = this.methodName == null ? this.methodDeclaration.getNameAsString() : this.methodName;
        this.accessor = this.accessor == null ? this.methodDeclaration.getAccessSpecifier().asString() : this.accessor;
    }

    private List<MethodParam> generateMethodParams() {
        List<MethodParam> output = new ArrayList<>();
        List<Parameter> list = this.methodDeclaration.getParameters();
        for (Parameter p : list) {
//            if (p.getAnnotations().size() > 0) {
//               TODO: Investigate this...
//            }
            MethodParam curr = new MethodParam();
            curr.setParameterType(p.getTypeAsString());
            curr.setParameterName(p.getName().getIdentifier());
            if (p.toString().startsWith("@")) {
                curr.setAnnotation(p.toString().substring(p.toString().indexOf(' ') + 1));
            }
            output.add(curr);
        }
        return output;
    }

    private List<AnnotationWrapper> generateAnnotations() {
        List<AnnotationWrapper> annotations = new ArrayList<>();
        for (AnnotationExpr exp : this.methodDeclaration.getAnnotations()) {
            AnnotationWrapper y = new AnnotationWrapper();
            y.setAnnotation(exp);
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            annotations.add(y);
        }
        return annotations;
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

    public MethodInfoBuilder withParentComponent(ComponentWrapper wrap) {
        this.parentComponent = wrap;
        return this;
    }

    private List<MethodInfoWrapper> generateSubmethods() {
        List<MethodInfoWrapper> subCalls = new ArrayList<>();
        this.methodDeclaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr n, Object arg) {
                super.visit(n, arg);
                subCalls.add(createMethodInfoWrapperFromExpr(n));
            }
        }, null);
        return subCalls;
    }

    //TODO: Resolve this!!!
    private MethodInfoWrapper createMethodInfoWrapperFromExpr(MethodCallExpr call) {
        MethodInfoWrapper out = new MethodInfoWrapper();
        List<MethodParam> arguments = new ArrayList<>();
        call.getArguments().stream().forEach(x -> {
            MethodParam param = new MethodParam();
            param.setParameterName(x.asNameExpr().getName().getIdentifier());
            param.setParameterType(x.asNameExpr().calculateResolvedType().toString());
            arguments.add(new MethodParam());
        });
        out.setMethodName(call.getNameAsExpression().getName().toString());
        out.setMethodParams(arguments);
        return out;
    }

}
