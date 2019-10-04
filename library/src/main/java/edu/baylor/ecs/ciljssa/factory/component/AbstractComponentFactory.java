package edu.baylor.ecs.ciljssa.factory.component;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.baylor.ecs.ciljssa.factory.methodinfo.MethodInfoFactory;
import edu.baylor.ecs.ciljssa.wrappers.AnnotationWrapper;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import edu.baylor.ecs.ciljssa.wrappers.MethodInfoWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComponentFactory implements IComponentFactory {

    protected List<MethodInfoWrapper> createMethods(ClassOrInterfaceDeclaration cls, IComponent parent) {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        MethodInfoFactory factory = new MethodInfoFactory();
        if (!cls.isInterface()) {
            List<MethodDeclaration> consts = cls.getMethods();
            consts.forEach(x -> {
                MethodInfoWrapper wrap = factory.createMethodInfoWrapper(x, parent);
                mds.add(wrap);
            });
        }
        return mds;
    }

    protected List<MethodInfoWrapper> createConstructors(ClassOrInterfaceDeclaration cls, IComponent parent) {
        List<MethodInfoWrapper> mds = new ArrayList<>();
        MethodInfoFactory factory = new MethodInfoFactory();
        if (!cls.isInterface()) {
            List<ConstructorDeclaration> consts = cls.getConstructors();
            consts.forEach(x -> {
                MethodInfoWrapper wrap = factory.createMethodInfoWrapper(x.asMethodDeclaration(), parent);
                mds.add(wrap);
            });
        }
        return mds;
    }

    protected List<AnnotationWrapper> initAnnotations(ClassOrInterfaceDeclaration cls) {
        List<AnnotationWrapper> annotations = new ArrayList<>();
        for (AnnotationExpr exp : cls.getAnnotations()) {
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
