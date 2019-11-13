package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassField;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.model.AnnotationValuePair;
import edu.baylor.ecs.ciljssa.model.ContainerType;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.LanguageFileType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        output.setInstanceName(cls.getName().asString() + "::ClassComponent");
        output.setContainerName(cls.getName().asString());
        output.setInstanceType(InstanceType.CLASSCOMPONENT);
        output.setMethodDeclarations(cls.getMethods());
        output.setPackageName(unit.getPackageDeclaration().map(NodeWithName::getNameAsString).orElse("N/A"));
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
            f.getVariables().accept(new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(VariableDeclarator v, Object arg) {
                    super.visit(v, arg);
                    field.setFieldName(v.getName().asString());
//                    pairs.add(new AnnotationValuePair(m.getName().toString(), m.getValue().toString()));
                }
            }, null);
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
            List<AnnotationComponent> annotations = AnnotationFactory.createAnnotationComponents(field, f.getAnnotations());
            field.setAnnotations(annotations);
            output.add(field);
        }
        return output;
    }

    private List<ClassComponent> createSubClasses(ClassOrInterfaceDeclaration cls) {
        // TODO
        return null;
    }

}
