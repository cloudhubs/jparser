package edu.baylor.ecs.ciljssa.factory.methodinfo;

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
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MethodInfoFactory {

    public MethodInfoWrapper createMethodInfoWrapper(MethodDeclaration dec, IComponent parent) {
        MethodInfoWrapper output = new MethodInfoBuilder().withParentComponent(parent)
                .withAccessor(dec.getAccessSpecifier().asString())
                .withAnnotations(generateAnnotations(dec))
                .withMethodParams(generateMethodParams(dec))
                .withSubMethods(generateSubmethods(dec))
                .withMethodName(dec.getNameAsString())
                .withReturnType(dec.getTypeAsString())
                .asStaticMethod(dec.isStatic())
                .build();
        return output;
    }

    private List<MethodParam> generateMethodParams(MethodDeclaration dec) {
        List<MethodParam> output = new ArrayList<>();
        List<Parameter> list = dec.getParameters();
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

    private List<AnnotationWrapper> generateAnnotations(MethodDeclaration dec) {
        List<AnnotationWrapper> annotations = new ArrayList<>();
        for (AnnotationExpr exp : dec.getAnnotations()) {
            AnnotationWrapper y = new AnnotationWrapper();
            y.setAnnotation(exp);
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            annotations.add(y);
        }
        return annotations;
    }

    private List<MethodInfoWrapper> generateSubmethods(MethodDeclaration dec) {
        List<MethodInfoWrapper> subCalls = new ArrayList<>();
        dec.accept(new VoidVisitorAdapter<Object>() {
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
