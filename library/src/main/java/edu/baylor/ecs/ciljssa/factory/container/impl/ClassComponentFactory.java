package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassField;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.model.ContainerType;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.LanguageFileType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassComponentFactory extends AbstractContainerFactory {

    private static AbstractContainerFactory INSTANCE;

    public final ContainerType TYPE = ContainerType.CLASS;

    private Map<ClassOrInterfaceDeclaration, Component> classOrInterfaceDeclarationComponentMap;

    private ClassComponentFactory() {
    }

    public static AbstractContainerFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClassComponentFactory();
        }
        return INSTANCE;
    }

    @Override
    public Component createComponent(ModuleComponent parent, ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        ClassComponent output = new ClassComponent();
        List<AnnotationComponent> annotations = initAnnotations(output, cls);
        List<ClassComponent> subClasses = createSubClasses(cls);
        output.setAnalysisUnit(unit);
        output.setAnnotations(annotations);
        output.setContainerType(ContainerType.CLASS);
        output.setCls(cls);
        output.setCompilationUnit(unit);
        output.setId(getId());
        output.setInstanceName(cls.getName().asString());
        output.setInstanceType(InstanceType.CLASSCOMPONENT);
        output.setMethodDeclarations(cls.getMethods());
        output.setPackageName("N/A"); // TODO: Set package name
        output.setParent(parent);
        output.setStereotype(createStereotype(cls));
        output.setId(getId());
        output.setClassFields(generateClassFields(cls));
        output.setRawSource(cls.toString());
        output.setPath(parent.getPath() + "/" + cls.getNameAsString() + "."
                + LanguageFileType.fromString(parent.getLanguage()).asString().toLowerCase()); //TODO: Use appropriate directory separater for OS
        List<Component> methods = createMethods(cls, output);
        List<Component> constructors = createConstructors(cls, output);
        output.setMethods(methods);
        output.setConstructors(constructors);
        output.setSubComponents(createMetaSubComponentAsList(output, methods, constructors, annotations, subClasses));
        return output;
    }

    private List<ClassField> generateClassFields(ClassOrInterfaceDeclaration cls) {
        List<ClassField> output = new ArrayList<>();
        for(FieldDeclaration f : cls.getFields()) {
            ClassField field = new ClassField();
            field.setType(f.getCommonType().getMetaModel().getType());
            field.setAccessor(AccessorType.fromString(f.getAccessSpecifier().asString()));
            for (Modifier m : f.getModifiers()) {
                switch(m.getKeyword()) {
                    case PUBLIC: field.setAccessor(AccessorType.PUBLIC); break;
                    case FINAL: field.setFinalField(true); break;
                    case STATIC: field.setStaticField(true); break;
                    case PROTECTED: field.setAccessor(AccessorType.PROTECTED); break;
                    case PRIVATE: field.setAccessor(AccessorType.PRIVATE); break;
                    case DEFAULT: field.setAccessor(AccessorType.DEFAULT); break;
                }
            }
//            f.getElementType().get
//            for (VariableDeclarator v : f.getVariables()) {
//                v.get
//            }
            AnnotationFactory.createAnnotationComponents(field, f.getAnnotations());
        }
        return output;
    }

    private List<ClassComponent> createSubClasses(ClassOrInterfaceDeclaration cls) {
        // TODO
        return null;
    }

}
