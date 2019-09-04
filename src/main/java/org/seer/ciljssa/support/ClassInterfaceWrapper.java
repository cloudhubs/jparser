package org.seer.ciljssa.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
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
    private String instanceName;

    private String[] constructors;
    private String[] methods;

    public ClassInterfaceWrapper(ClassOrInterfaceDeclaration cls){
        this.cls = cls;
        this.classOrInterface = isClass() ? ClassOrInterface.Class : ClassOrInterface.Interface;
        this.instanceName = cls.getNameAsString();
        this.constructors = generateConstructors();
        this.methods = generateMethods();
    }

    public boolean isClass() {
        return cls.isInnerClass() || cls.isLocalClassDeclaration();
    }

    public boolean isInterface() {
        return cls.isInterface();
    }

    private String[] generateConstructors() {
        ArrayList<String> constructors = new ArrayList<>();
        Arrays.stream(cls.getConstructors().toArray()).forEach(obj -> {
            ConstructorDeclaration cd = (ConstructorDeclaration) obj;
            constructors.add(cd.getNameAsString());
        });
        return constructors.toArray(new String[0]);
    }

    private String[] generateMethods() {
        return new String[]{};
    }

}
