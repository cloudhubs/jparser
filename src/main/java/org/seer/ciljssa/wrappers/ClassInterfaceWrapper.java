package org.seer.ciljssa.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassInterfaceWrapper {

    enum ClassOrInterface {
        Class, Interface
    }

    @JsonIgnore
    private ClassOrInterfaceDeclaration cls;

    @JsonProperty(value = "declaration_type")
    private String classOrInterface;
    @JsonProperty(value = "name")
    private String instanceName;
    private MethodInfoWrapper[] methods;
    private MethodInfoWrapper[] constructors;

    public ClassInterfaceWrapper(ClassOrInterfaceDeclaration cls){
        this.cls = cls;
        this.classOrInterface = isClass() ? ClassOrInterface.Class.toString() : ClassOrInterface.Interface.toString();
        this.instanceName = cls.getNameAsString();
        this.constructors = createConstructors();
        this.methods = createMethodInfoWrappers();
    }

    @JsonIgnore
    public boolean isClass() {
        return cls.isClassOrInterfaceDeclaration() && !cls.isInterface();
    }

    @JsonIgnore
    public boolean isInterface() {
        return cls.isInterface();
    }

    private MethodInfoWrapper[] createConstructors() {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        Arrays.stream(cls.getConstructors().toArray()).forEach(obj -> {
            ConstructorDeclaration cd = (ConstructorDeclaration) obj;

            MethodInfoWrapper md = new MethodInfoWrapper();
            md.setAnnotations(initAnnotations(cd.getAnnotations()));
            md.setMethodParams(initParameters(cd.getParameters()));

            md.setAccessor(cd.getAccessSpecifier().asString());
            md.setMethodName(cd.getNameAsString());
            mds.add(md);
        });
        return mds.toArray(new MethodInfoWrapper[0]);
    }

    //TODO: Separate constructor into its own methodinfowrapper

    private MethodInfoWrapper[] createMethodInfoWrappers() {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        Arrays.stream(cls.getMethods().toArray()).forEach(obj -> {
            MethodDeclaration cd = (MethodDeclaration) obj;

            MethodInfoWrapper md = new MethodInfoWrapper();
            md.setAnnotations(initAnnotations(cd.getAnnotations()));
            md.setMethodParams(initParameters(cd.getParameters()));

            md.setAccessor(cd.getAccessSpecifier().asString());
            md.setMethodName(cd.getNameAsString());
            md.setReturnType(cd.getTypeAsString());
            md.setStaticMethod(cd.isStatic());
            mds.add(md);
        });
        return mds.toArray(new MethodInfoWrapper[0]);
    }

    private List<String[]> initParameters(List<Parameter> list) {
        List<String[]> output = new ArrayList<>();
        String[] iter;
        for (Parameter p : list) {
            if (p.toString().startsWith("@")) {
                String current = p.toString();
                iter = new String[2];
                iter[0] = current.substring(current.indexOf(' ') + 1);
                iter[1] = current.substring(0, current.indexOf(' '));
            } else {
                iter = new String[1];
                iter[0] = p.toString();
            }
            output.add(iter);
        }

        return output;
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
