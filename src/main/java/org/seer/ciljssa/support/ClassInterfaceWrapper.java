package org.seer.ciljssa.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import lombok.Data;
import org.apache.tomcat.util.http.Parameters;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassInterfaceWrapper {

    /**
     * Presumably this will help JSON wrap to either "Class" and "Interface" under type,
     * instead of something gross like "org.seer.ciljssa.support.ClassInterfaceWrapper.ClassOrInterface.CLASS"
     * This is just my assumption, so this enum may be unnecessarily complex.
     */
    enum ClassOrInterface {
        Class("Class"), Interface("Interface");
        private final String value;

        ClassOrInterface(final String val){
            this.value = val;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    @JsonIgnore
    private ClassOrInterfaceDeclaration cls;

    @JsonProperty(value = "declaration_type")
    private ClassOrInterface classOrInterface;
    @JsonProperty(value = "method_description")
    private MethodInfoWrapper[] methods;

    private String instanceName;
    private String[] constructors;

    public ClassInterfaceWrapper(ClassOrInterfaceDeclaration cls){
        this.cls = cls;
        this.classOrInterface = isClass() ? ClassOrInterface.Class : ClassOrInterface.Interface;
        this.instanceName = cls.getNameAsString();
        this.constructors = createConstructors();
        this.methods = createMethodInfoWrappers();
    }

    public boolean isClass() {
        return cls.isInnerClass() || cls.isLocalClassDeclaration();
    }

    public boolean isInterface() {
        return cls.isInterface();
    }

    private String[] createConstructors() {
        ArrayList<String> cds = new ArrayList<>();
        Arrays.stream(cls.getConstructors().toArray()).forEach(obj -> {
            ConstructorDeclaration cd = (ConstructorDeclaration) obj;
            cds.add(cd.getNameAsString());
        });
        return cds.toArray(new String[0]);
    }

    //TODO: Separate constructor into its own methodinfowrapper

    private MethodInfoWrapper[] createMethodInfoWrappers() {
        ArrayList<MethodInfoWrapper> mds = new ArrayList<>();
        Arrays.stream(cls.getMethods().toArray()).forEach(obj -> {
            MethodDeclaration cd = (MethodDeclaration) obj;
            AnnotationExpr[] annos = cd.getAnnotations().toArray(new AnnotationExpr[0]);
            //Parameters[] pars = cd.getParameters().toArray(new Parameters[0]);

            ArrayList<String> annotations = new ArrayList<>();
            ArrayList<String> params = new ArrayList<>();

            Arrays.stream(annos).forEach(x -> {annotations.add(x.getMetaModel().toString()); }); //TODO: This should be fleshed out, just part of prototyping
            //Arrays.stream(pars).forEach(x -> {params.add(x.toString());});

            MethodInfoWrapper md = new MethodInfoWrapper();

            md.setAccessor(cd.getAccessSpecifier().asString());
            md.setAnnotations(annotations.toArray(new String[0]));
            md.setMethodName(cd.getNameAsString());
            md.setReturnType(cd.getTypeAsString());
            md.setStaticMethod(cd.isStatic());
            //md.setMethodParams(params.toArray(new String[0]));
            mds.add(md);
        });
        return mds.toArray(new MethodInfoWrapper[0]);
    }

}
