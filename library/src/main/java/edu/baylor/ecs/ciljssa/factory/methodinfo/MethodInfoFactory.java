package edu.baylor.ecs.ciljssa.factory.methodinfo;

import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.MethodInfoBuilder;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MethodInfoFactory {

    public MethodInfoComponent createMethodInfoWrapper(MethodDeclaration dec, IContainerComponent parent) {
        MethodInfoComponent output = new MethodInfoBuilder().withParentComponent(parent)
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

    public MethodInfoComponent createMethodInfoWrapperFromConstructor(ConstructorDeclaration x, IContainerComponent parent) {
        MethodInfoComponent output = new MethodInfoBuilder().withParentComponent(parent)
                .withAccessor(x.getAccessSpecifier().asString())
                .withAnnotations(generateAnnotations(x.asMethodDeclaration()))
                .withMethodParams(generateMethodParams(x.asMethodDeclaration()))
                .withSubMethods(generateSubmethods(x.asMethodDeclaration()))
                .withMethodName(x.getNameAsString())
                .asStaticMethod(x.isStatic())
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

    private List<AnnotationComponent> generateAnnotations(MethodDeclaration dec) {
        List<AnnotationComponent> annotations = new ArrayList<>();
        for (AnnotationExpr exp : dec.getAnnotations()) {
            AnnotationComponent y = new AnnotationComponent();
            y.setAnnotation(exp);
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            annotations.add(y);
        }
        return annotations;
    }

    private List<MethodInfoComponent> generateSubmethods(MethodDeclaration dec) {
        List<MethodInfoComponent> subCalls = new ArrayList<>();
        dec.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr n, Object arg) {
                super.visit(n, arg);
                subCalls.add(createMethodInfoWrapperFromExpr(n));
            }
        }, null);
        return subCalls;
    }

    private MethodInfoComponent createMethodInfoWrapperFromExpr(MethodCallExpr call) {
        MethodInfoComponent out = new MethodInfoComponent();
        List<MethodParam> arguments = new ArrayList<>();
        call.getArguments().stream().forEach(x -> {
            MethodParam param = new MethodParam();
            //param.setParameterName(x.asNameExpr().getName().getIdentifier());
            //param.setParameterType(x.asNameExpr().calculateResolvedType().toString());
            arguments.add(new MethodParam());
        });
        out.setMethodName(call.getNameAsExpression().getName().toString());
        out.setMethodParams(arguments);
        return out;
    }
}
