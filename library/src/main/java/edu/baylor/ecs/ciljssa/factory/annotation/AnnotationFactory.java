package edu.baylor.ecs.ciljssa.factory.annotation;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class AnnotationFactory {

    public static List<AnnotationComponent> createAnnotationComponents(NodeList<AnnotationExpr> annotations2) {
        List<AnnotationComponent> annotations = new ArrayList<>();
        for (AnnotationExpr exp : annotations2) {
            AnnotationComponent y = new AnnotationComponent();
            y.setAnnotation(exp);
            y.setAnnotationMetaModel(exp.getMetaModel().toString());
            y.setAsString(exp.getName().asString());
            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
            annotations.add(y);
        }
        return annotations;
    }

}
