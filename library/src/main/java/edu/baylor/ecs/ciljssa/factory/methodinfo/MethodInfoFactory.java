package edu.baylor.ecs.ciljssa.factory.methodinfo;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.types.ResolvedType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the class used for generating MethodInfoComponents from Java Parser MethodDeclaration objects.
 * The main entry point is "createMethodInfoWrapper(MethodDeclaration, ContainerComponent)".
 * Pass in the MethodDeclaration and the class or interface component that contains it.
 */
public class MethodInfoFactory {

    private static MethodInfoFactory INSTANCE;

    // The ID to increment
    private long idToCreate;
    // The mapping from Expression strings to MethodInfoComponents
    private Map<String, MethodInfoComponent> methodInfoExpressions;
    // The mapping from method declarations to method info components
    private Map<MethodDeclaration, MethodInfoComponent> methodInfoDeclarations;
    // The mapping from constructor declarations to method info components
    private Map<ConstructorDeclaration, MethodInfoComponent> constructorInfoDeclarations;

    public static MethodInfoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MethodInfoFactory();
        }
        return INSTANCE;
    }

    private MethodInfoFactory() {
        this.methodInfoDeclarations = new HashMap<>();
        this.methodInfoExpressions = new HashMap<>();
        this.constructorInfoDeclarations = new HashMap<>();
        this.idToCreate = 1;
    }

    private long iterateId() {
        long temp = idToCreate;
        idToCreate = idToCreate + 1;
        return temp;
    }

    public MethodInfoComponent createMethodInfoWrapper(MethodDeclaration dec, ContainerComponent parent) {
        MethodInfoComponent output = new MethodInfoComponent();
        if (methodInfoDeclarations.containsKey(dec)) {
            output = methodInfoDeclarations.get(dec);
        } else {
            output = new MethodInfoBuilder().withParentComponent(parent)
                    .withAccessor(AccessorType.fromString(dec.getAccessSpecifier().asString()))
                    .withAnnotations(generateAnnotations(output, dec))
                    .withMethodParams(generateMethodParams(dec))
                    .withSubMethods(generateSubmethods(dec))
                    .withMethodName(dec.getNameAsString())
                    .withReturnType(dec.getTypeAsString())
                    .asStaticMethod(dec.isStatic())
                    .asAbstractMethod(dec.isAbstract())
                    .withId(iterateId())
                    .build();
            methodInfoDeclarations.put(dec, output);
        }
        return output;
    }

    public MethodInfoComponent createMethodInfoWrapperFromConstructor(ConstructorDeclaration x, Component parent) {
        MethodInfoComponent output = new MethodInfoComponent();
        if (constructorInfoDeclarations.containsKey(x)) {
            output = constructorInfoDeclarations.get(x);
        } else {
            output = new MethodInfoBuilder().withParentComponent(parent)
                    .withAccessor(AccessorType.fromString(x.getAccessSpecifier().asString()))
                    .withAnnotations(generateAnnotationsConstructor(output, x.asConstructorDeclaration()))
                    .withMethodParams(generateMethodParams(x.getParameters()))
                    .withSubMethods(generateSubmethodsConstructor(x))
                    .withMethodName(x.getNameAsString())
                    .asStaticMethod(x.isStatic())
                    .asAbstractMethod(x.isAbstract())
                    .build();
            constructorInfoDeclarations.put(x, output);
        }
        return output;
    }

    private List<MethodParam> generateMethodParams(List<Parameter> parameters) {
        List<MethodParam> output = new ArrayList<>();
        for (Parameter p : parameters) {
            MethodParam curr = new MethodParam();
            //curr.setParameterType(p.getTypeAsString());
            p.getType().
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

    private List<AnnotationComponent> generateAnnotationsConstructor(Component parent, ConstructorDeclaration dec) {
        return AnnotationFactory.createAnnotationComponents(parent, dec.getAnnotations());
    }

    private List<AnnotationComponent> generateAnnotations(Component parent, MethodDeclaration dec) {
        return AnnotationFactory.createAnnotationComponents(parent, dec.getAnnotations());
    }

    private List<MethodInfoComponent> generateSubmethodsConstructor(ConstructorDeclaration dec) {
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

    /**
     * Subroutine creation is handled here.
     * @param call
     * @return
     */
    private MethodInfoComponent createMethodInfoWrapperFromExpr(MethodCallExpr call) {
        if (call == null)
            return null;
        MethodInfoComponent out = new MethodInfoComponent();
        if (methodInfoExpressions.containsKey(call.getNameAsString())) {
            out = methodInfoExpressions.get(call.getNameAsString());
        } else {
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
            out.setId(iterateId());
            methodInfoExpressions.put(call.getNameAsString(), out);
        }
        return out;
    }
}
