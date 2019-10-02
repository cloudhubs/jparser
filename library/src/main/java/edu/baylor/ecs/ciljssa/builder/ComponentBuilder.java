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

public class ComponentBuilder {

    private ComponentWrapper output;

    private ClassOrInterface builderType;
    private ClassOrInterfaceDeclaration cls;
    private CompilationUnit analysisUnit;
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

    public ComponentWrapper build() {
        switch (builderType){
            case CLASS: output = new ClassWrapper(); ((ClassWrapper) output).setConstructors(this.constructors); break;
            case INTERFACE: output = new InterfaceWrapper(); break;
            default: output = null;
        }
        output.setAnalysisUnit(this.analysisUnit);
        output.setClassOrInterface(this.builderType);
        output.setMethods(this.methods);
        output.setCls(this.cls);
        output.setInstanceName(this.instanceName);
        output.setMethodDeclarations(this.methodDeclarations);
        return output;
    }

    public ClassOrInterface getBuilderType() {
        return builderType;
    }

    private List<MethodInfoWrapper> createConstructors() {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        List<ConstructorDeclaration> consts = cls.getConstructors();
        consts.stream().forEach(x -> {
            MethodInfoBuilder builder = new MethodInfoBuilder(x.asMethodDeclaration(), output);
            mds.add(builder.build());
        });
        return mds;
    }

    private List<AnnotationWrapper> initAnnotations(List<AnnotationExpr> list) {
        List<AnnotationWrapper> annotations = new ArrayList<>();
        for (AnnotationExpr exp : list) {
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
