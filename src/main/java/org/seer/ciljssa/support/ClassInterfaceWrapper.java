package org.seer.ciljssa.support;

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
        CLASS("Class"), INTERFACE("Interface");
        private final String value;

        ClassOrInterface(final String val){
            this.value = val;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    private ClassOrInterfaceDeclaration cls;

    private ClassOrInterface classOrInterface;

    private String[] constructors;
    private String[] methods;

    // Not needed for Spring JSON?
    public ClassInterfaceWrapper(){
    }

    public ClassInterfaceWrapper(ClassOrInterfaceDeclaration cls){
        this.cls = cls;
        this.classOrInterface = isClass() ? ClassOrInterface.CLASS : ClassOrInterface.INTERFACE;
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
        return (String[]) constructors.toArray();
    }

    private String[] generateMethods() {
        return new String[]{};
    }

}
