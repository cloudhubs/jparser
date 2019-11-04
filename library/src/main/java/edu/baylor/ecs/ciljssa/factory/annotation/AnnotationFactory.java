package edu.baylor.ecs.ciljssa.factory.annotation;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class AnnotationFactory {

    // TODO: Basic, naive, doesn't provide as much info
    public static AnnotationComponent createAnnotationFromString(Component parent, String annotation) {
        AnnotationComponent an = new AnnotationComponent();
        an.setParent(parent);
        an.setPath(parent.getPath());
        an.setMetaModelFieldName(annotation);
        an.setInstanceName(annotation+"::AnnotationComponent");
        an.setAsString(annotation);
        an.setInstanceType(InstanceType.ANNOTATIONCOMPONENT);
        an.setAsString(annotation);
        return an;
    }

    public static List<AnnotationComponent> createAnnotationComponents(Component parent, NodeList<AnnotationExpr> annotations2) {
        List<AnnotationComponent> annotations = new ArrayList<>();
        for (AnnotationExpr exp : annotations2) {
            AnnotationComponent y = new AnnotationComponent();
            y.setAnnotation(exp);
            y.setParent(parent);
            y.setInstanceType(InstanceType.ANNOTATIONCOMPONENT);
            y.setInstanceName(exp.getMetaModel().getMetaModelFieldName()+"::AnnotationComponent");
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            y.setPath(parent.getPath());
            annotations.add(y);
        }
        return annotations;
    }

}
