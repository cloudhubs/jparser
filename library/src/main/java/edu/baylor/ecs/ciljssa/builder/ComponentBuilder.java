package edu.baylor.ecs.ciljssa.builder;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.wrappers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Could keep this class and make the factory use this builder
 */

@Deprecated
public class ComponentBuilder {

    private IComponent output;

    private ClassOrInterface builderType;
    private ClassOrInterfaceDeclaration cls;
    private String instanceName;
    private List<MethodInfoWrapper> methods;
    private List<MethodInfoWrapper> constructors;
    private List<MethodDeclaration> methodDeclarations;
    private List<AnnotationWrapper> componentAnnotations;

    public ComponentBuilder(ClassOrInterfaceDeclaration cls) {
        this.cls = cls;
        this.builderType = cls.isClassOrInterfaceDeclaration() && !cls.isInterface()
                ? ClassOrInterface.CLASS : ClassOrInterface.INTERFACE;
    }

    public IComponent build() {
        switch (builderType){
            case CLASS: output = new ClassComponent(); ((ClassComponent) output).setConstructors(createConstructors()); break;
            case INTERFACE: output = new InterfaceComponent(); break;
            default: output = null;
        }
        output.setClassOrInterface(this.builderType);
        output.setMethods(this.methods);
        output.setCls(this.cls);
        output.setInstanceName(this.instanceName);
        output.setMethodDeclarations(this.methodDeclarations);
        output.setComponentAnnotations(initAnnotations());
        return output;
    }

    public ClassOrInterface getBuilderType() {
        return builderType;
    }

    private List<MethodInfoWrapper> createConstructors() {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        if (!this.cls.isInterface()) {
            List<ConstructorDeclaration> consts = this.cls.getConstructors();
            consts.stream().forEach(x -> {
                MethodInfoBuilder builder = new MethodInfoBuilder(x.asMethodDeclaration(), output);
                mds.add(builder.build());
            });
        }
        return mds;
    }

    private List<AnnotationWrapper> initAnnotations() {
        List<AnnotationWrapper> annotations = new ArrayList<>();
        for (AnnotationExpr exp : this.cls.getAnnotations()) {
            AnnotationWrapper y = new AnnotationWrapper();
            y.setAnnotation(exp);
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            annotations.add(y);
        }
        return annotations;
    }

}
