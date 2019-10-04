package edu.baylor.ecs.ciljssa.factory.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.MethodInfoBuilder;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import edu.baylor.ecs.ciljssa.wrappers.AnnotationWrapper;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import edu.baylor.ecs.ciljssa.wrappers.MethodInfoWrapper;

import java.util.ArrayList;
import java.util.List;

public class MethodInfoFactory {

    private MethodDeclaration methodDeclaration;
    private IComponent parentComponent;

    public MethodInfoFactory(MethodDeclaration d, IComponent parent) {
        this.methodDeclaration = d;
        this.parentComponent = parent;
    }

    public MethodInfoWrapper createMethodInfoWrapper() {
        MethodInfoWrapper output = new MethodInfoBuilder().withParentComponent(this.parentComponent)
                .withAccessor(this.methodDeclaration.getAccessSpecifier().asString())
                .withAnnotations(generateAnnotations())
                .withMethodName(this.methodDeclaration.getNameAsString())
                .withMethodParams(generateMethodParams())
                .withSubMethods(generateSubmethods())
                .withReturnType(this.methodDeclaration.getTypeAsString())
                .asStaticMethod(this.methodDeclaration.isStatic())
                .build();
        return output;
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
