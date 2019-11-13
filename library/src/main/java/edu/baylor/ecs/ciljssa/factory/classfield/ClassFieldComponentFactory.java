package edu.baylor.ecs.ciljssa.factory.classfield;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassField;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.model.AccessorType;

import java.util.ArrayList;
import java.util.List;

public class ClassFieldComponentFactory {

    public List<ClassField> createClassField(List<FieldDeclaration> declarations) {
        List<ClassField> output = new ArrayList<>();
        for(FieldDeclaration f : declarations) {
            ClassField field = new ClassField();
            field.setType(f.getCommonType().getMetaModel().getType());
            field.setAccessor(AccessorType.fromString(f.getAccessSpecifier().asString()));
            System.out.println(f);
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

}
