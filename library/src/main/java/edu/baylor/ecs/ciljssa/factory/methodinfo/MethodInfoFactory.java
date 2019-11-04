package edu.baylor.ecs.ciljssa.factory.methodinfo;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.MethodInfoBuilder;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.component.impl.MethodParam;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class used for generating MethodInfoComponents from Java Parser MethodDeclaration objects.
 * The main entry point is "createMethodInfoWrapper(MethodDeclaration, ContainerComponent)".
 * Pass in the MethodDeclaration and the class or interface component that contains it.
 */
@NoArgsConstructor
public class MethodInfoFactory {

    public static MethodInfoComponent createMethodInfoWrapper(MethodDeclaration dec, ContainerComponent parent) {
        MethodInfoComponent output = new MethodInfoComponent();
        output = new MethodInfoBuilder().withParentComponent(parent)
                .withAccessor(AccessorType.fromString(dec.getAccessSpecifier().asString()))
                .withAnnotations(generateAnnotations(output, dec))
                .withMethodParams(generateMethodParams(dec.getParameters()))
                .withSubMethods(generateSubmethods(dec))
                .withMethodName(dec.getNameAsString())
                .withReturnType(dec.getTypeAsString())
                .asStaticMethod(dec.isStatic())
                .asAbstractMethod(dec.isAbstract())
                .build();
        return output;
    }

    public static MethodInfoComponent createMethodInfoWrapperFromConstructor(ConstructorDeclaration x, Component parent) {
        MethodInfoComponent output = new MethodInfoComponent();
        output = new MethodInfoBuilder().withParentComponent(parent)
                .withAccessor(AccessorType.fromString(x.getAccessSpecifier().asString()))
                .withAnnotations(generateAnnotationsConstructor(output, x.asConstructorDeclaration()))
                .withMethodParams(generateMethodParams(x.getParameters()))
                .withSubMethods(generateSubmethodsConstructor(x))
                .withMethodName(x.getNameAsString())
                .asStaticMethod(x.isStatic())
                .asAbstractMethod(x.isAbstract())
                .build();
        return output;
    }

    private static List<MethodParam> generateMethodParams(List<Parameter> parameters) {
        List<MethodParam> output = new ArrayList<>();
        for (Parameter p : parameters) {
            MethodParam curr = new MethodParam();
            curr.setParameterType(p.getTypeAsString());
            curr.setParameterName(p.getName().getIdentifier());
            curr.setType(p.getClass());
            if (p.toString().startsWith("@")) {
                AnnotationComponent comp = new AnnotationComponent();
                curr.setAnnotation(AnnotationFactory.createAnnotationFromString(curr, p.toString().substring(p.toString().indexOf(' ') + 1)));
            }
            output.add(curr);
        }
        return output;
    }

    private static List<AnnotationComponent> generateAnnotationsConstructor(Component parent, ConstructorDeclaration dec) {
        return AnnotationFactory.createAnnotationComponents(parent, dec.getAnnotations());
    }

    private static List<AnnotationComponent> generateAnnotations(Component parent, MethodDeclaration dec) {
        return AnnotationFactory.createAnnotationComponents(parent, dec.getAnnotations());
    }

    private static List<MethodInfoComponent> generateSubmethodsConstructor(ConstructorDeclaration dec) {
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

    private static List<MethodInfoComponent> generateSubmethods(MethodDeclaration dec) {
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

    /**
     * Subroutine creation is handled here.
     * @param call
     * @return
     */
    private static MethodInfoComponent createMethodInfoWrapperFromExpr(MethodCallExpr call) {
        MethodInfoComponent out = new MethodInfoComponent();
        List<MethodParam> arguments = new ArrayList<>();
        call.getArguments().forEach(x -> {
            MethodParam param = new MethodParam(); //TODO: Doesn't work for expr
            if (x instanceof NameExpr) {
                param.setParameterName(x.asNameExpr().getName().getIdentifier());
            }
            param.setType(x.getClass());
           // param.setParameterType(x.asNameExpr().calculateResolvedType().toString());
            arguments.add(param);
        });
        out.setMethodName(call.getNameAsExpression().getName().toString());
        out.setMethodParams(arguments);
        return out;
    }
}
