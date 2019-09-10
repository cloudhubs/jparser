package org.seer.ciljssa.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import lombok.Data;
import com.github.javaparser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.Arrays;

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
    @JsonProperty(value = "method_description")
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
        ArrayList<MethodInfoWrapper> cds = new ArrayList<>();
        ArrayList<MethodInfoWrapper> mds = new ArrayList<>();
        Arrays.stream(cls.getConstructors().toArray()).forEach(obj -> {
            ConstructorDeclaration cd = (ConstructorDeclaration) obj;
            AnnotationExpr[] annos = cd.getAnnotations().toArray(new AnnotationExpr[0]);
            Parameter[] pars = cd.getParameters().toArray(new Parameter[0]);

            ArrayList<AnnotationWrapper> annotations = new ArrayList<>();
            ArrayList<String> params = new ArrayList<>();

            Arrays.stream(annos).forEach(x -> {
                AnnotationWrapper y = new AnnotationWrapper();
                y.setAnnotationMetaModel(x.getMetaModel().toString());
                y.setAsString(x.getName().asString());
                y.setPackageName(x.getMetaModel().getPackageName());
                y.setMetaModelFieldName(x.getMetaModel().getMetaModelFieldName());
                annotations.add(y);
            }); //TODO: This should be fleshed out, just part of prototyping
            Arrays.stream(pars).forEach(x -> params.add(x.toString()));

            MethodInfoWrapper md = new MethodInfoWrapper();

            md.setAccessor(cd.getAccessSpecifier().asString());
            md.setAnnotations(annotations.toArray(new AnnotationWrapper[0]));
            md.setMethodName(cd.getNameAsString());
            md.setMethodParams(params.toArray(new String[0]));
            mds.add(md);
        });
        return mds.toArray(new MethodInfoWrapper[0]);
    }

    //TODO: Separate constructor into its own methodinfowrapper

    private MethodInfoWrapper[] createMethodInfoWrappers() {
        ArrayList<MethodInfoWrapper> mds = new ArrayList<>();
        Arrays.stream(cls.getMethods().toArray()).forEach(obj -> {
            MethodDeclaration cd = (MethodDeclaration) obj;
            AnnotationExpr[] annos = cd.getAnnotations().toArray(new AnnotationExpr[0]);
            Parameter[] pars = cd.getParameters().toArray(new Parameter[0]);

            ArrayList<AnnotationWrapper> annotations = new ArrayList<>();
            ArrayList<String> params = new ArrayList<>();

            Arrays.stream(annos).forEach(x -> {
                AnnotationWrapper y = new AnnotationWrapper();
                y.setAnnotationMetaModel(x.getMetaModel().toString());
                y.setAsString(x.getName().asString());
                y.setPackageName(x.getMetaModel().getPackageName());
                y.setMetaModelFieldName(x.getMetaModel().getMetaModelFieldName());
                annotations.add(y);
            });
            Arrays.stream(pars).forEach(x -> params.add(x.toString()));

            MethodInfoWrapper md = new MethodInfoWrapper();

            md.setAccessor(cd.getAccessSpecifier().asString());
            md.setAnnotations(annotations.toArray(new AnnotationWrapper[0]));
            md.setMethodName(cd.getNameAsString());
            md.setReturnType(cd.getTypeAsString());
            md.setStaticMethod(cd.isStatic());
            md.setMethodParams(params.toArray(new String[0]));
            mds.add(md);
        });
        return mds.toArray(new MethodInfoWrapper[0]);
    }

}
