package edu.baylor.ecs.ciljssa.factory.methodinfo;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            long id = iterateId();
            List<AnnotationComponent> annotations = generateAnnotations(output, dec);
            List<MethodParam> parameters = generateMethodParams(dec.getParameters());
            List<MethodInfoComponent> subMethods = generateSubmethods(dec);
            List<Component> subComponents = flattenSubComponentList(annotations, parameters, subMethods);
            output = new MethodInfoBuilder().withParentComponent(parent)
                    .withAccessor(AccessorType.fromString(dec.getAccessSpecifier().asString()))
                    .withAnnotations(annotations)
                    .withMethodParams(parameters)
                    .withSubMethods(subMethods)
                    .withMethodName(dec.getNameAsString())
                    .withReturnType(dec.getTypeAsString())
                    .asStaticMethod(dec.isStatic())
                    .asAbstractMethod(dec.isAbstract())
                    .withId(id)
                    .withRawSource(dec.getBody().isPresent() ? dec.getBody().get().toString() : "N/A")
                    .withStatements(dec.getBody().isPresent() ? dec.getBody().get().getStatements().stream()
                            .map(Node::toString).collect(Collectors.toList()) : new ArrayList<>())
                    .withInstanceName(parent.getInstanceName() + "::ModuleComponent::" + id)
                    .withPath(parent.getPath() + "::" + dec.getNameAsString())
                    .withPackageName(parent.getPackageName() + "." + dec.getNameAsString())
                    .withSubComponents(subComponents)
                    .build();
            methodInfoDeclarations.put(dec, output);
        }
        return output;
    }

    private List<Component> flattenSubComponentList(List<AnnotationComponent> annotations,
                                                          List<MethodParam> parameters,
                                                          List<MethodInfoComponent> subMethods) {
        List<List<Component>> unflattenedMap = new ArrayList<>();
        unflattenedMap.add(annotations.stream().map(x -> (Component) x).collect(Collectors.toList()));
        unflattenedMap.add(parameters.stream().map(x -> (Component) x).collect(Collectors.toList()));
        unflattenedMap.add(subMethods.stream().map(x -> (Component) x).collect(Collectors.toList()));
        return unflattenedMap.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public MethodInfoComponent createMethodInfoWrapperFromConstructor(ConstructorDeclaration dec, Component parent) {
        MethodInfoComponent output = new MethodInfoComponent();
        if (constructorInfoDeclarations.containsKey(dec)) {
            output = constructorInfoDeclarations.get(dec);
        } else {
            long id = iterateId();
            List<AnnotationComponent> annotations = generateAnnotationsConstructor(output, dec);
            List<MethodParam> parameters = generateMethodParams(dec.getParameters());
            List<MethodInfoComponent> subMethods = generateSubmethodsConstructor(dec);
            List<Component> subComponents = flattenSubComponentList(annotations, parameters, subMethods);
            output = new MethodInfoBuilder().withParentComponent(parent)
                    .withAccessor(AccessorType.fromString(dec.getAccessSpecifier().asString()))
                    .withAnnotations(annotations)
                    .withMethodParams(parameters)
                    .withSubMethods(subMethods)
                    .withMethodName(dec.getNameAsString())
                    .withReturnType("N/A")
                    .asStaticMethod(dec.isStatic())
                    .asAbstractMethod(dec.isAbstract())
                    .withId(id)
                    .withRawSource(dec.getBody().toString())
                    .withStatements(dec.getBody().getStatements().stream()
                            .map(Node::toString).collect(Collectors.toList()))
                    .withInstanceName(parent.getInstanceName() + "::ModuleComponent::" + id)
                    .withPath(parent.getPath() + "::" + dec.getNameAsString())
                    .withPackageName(parent.getPackageName() + "." + dec.getNameAsString())
                    .withSubComponents(subComponents)
                    .build();
            constructorInfoDeclarations.put(dec, output);
        }
        return output;
    }

    private List<MethodParam> generateMethodParams(List<Parameter> parameters) {
        List<MethodParam> output = new ArrayList<>();
        for (Parameter p : parameters) {
            MethodParam curr = new MethodParam();
            //curr.setParameterType(p.getTypeAsString());
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
                param.setType(x.calculateResolvedType().getClass()); //TODO: Always parameter object
                param.setParameterType(x.toString());
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
