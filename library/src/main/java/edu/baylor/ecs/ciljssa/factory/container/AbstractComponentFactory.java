package edu.baylor.ecs.ciljssa.factory.container;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.MetaSubComponent;
import edu.baylor.ecs.ciljssa.factory.methodinfo.MethodInfoFactory;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: More of a container factory isn't it
 */
public abstract class AbstractComponentFactory {

    private Long idEnumerator = 0L;

    protected Long getId() {
        idEnumerator++;
        return idEnumerator;
    }

    private Component createMetaSubComponent(Component parent, List<MethodInfoComponent> methods,
                                             List<MethodInfoComponent> constructors,
                                             List<AnnotationComponent> annotations,
                                             List<ClassComponent> subClasses) {
        MetaSubComponent component = new MetaSubComponent();
        component.setMethodInfoComponents(methods);
        component.setConstructorComponents(constructors);
        component.setAnnotationComponents(annotations);
        component.setSubClassComponents(subClasses);
        component.setInstanceType(InstanceType.META);
        component.setParent(parent);
        component.setInstanceName(parent.getInstanceName() + "::MetaSubComponent");
        component.setPath(parent.getPath());
        component.setPackageName(parent.getPackageName());
        return component;
    }

    protected List<Component> createMetaSubComponentAsList(Component parent, List<MethodInfoComponent> methods,
                                                      List<MethodInfoComponent> constructors,
                                                      List<AnnotationComponent> annotations,
                                                      List<ClassComponent> subClasses) {
        List<Component> output = new ArrayList<>();
        output.add(createMetaSubComponent(parent, methods, constructors, annotations, subClasses));
        return output;
    }

    protected ContainerStereotype createStereotype(ClassOrInterfaceDeclaration cls) {
        return ContainerStereotype.FABRICATED;
    }

    protected List<MethodInfoComponent> createMethods(ClassOrInterfaceDeclaration cls, ContainerComponent parent) {
        List<MethodInfoComponent> mds = new ArrayList<>();
        MethodInfoFactory factory = new MethodInfoFactory();
        if (!cls.isInterface()) {
            List<MethodDeclaration> consts = cls.getMethods();
            consts.forEach(x -> {
                MethodInfoComponent wrap = factory.createMethodInfoWrapper(x, parent);
                mds.add(wrap);
            });
        }
        return mds;
    }

    //TODO: See old commits
    protected List<MethodInfoComponent> createConstructors(ClassOrInterfaceDeclaration cls, Component parent) {
        List<MethodInfoComponent> mds = new ArrayList<>();
        MethodInfoFactory factory = new MethodInfoFactory();
        /*if (!cls.isInterface()) {
            List<ConstructorDeclaration> consts = cls.getConstructors(); // TODO: Does not work with constructors
            consts.forEach(x -> {
                MethodInfoWrapper wrap = factory.createMethodInfoWrapperFromConstructor(x, parent);
                mds.add(wrap);
            });
        }*/
        return mds;
    }

    protected List<AnnotationComponent> initAnnotations(ClassOrInterfaceDeclaration cls) {
        return getAnnotationComponents(cls.getAnnotations(), cls);
    }

    private static List<AnnotationComponent> getAnnotationComponents(NodeList<AnnotationExpr> annotations2, ClassOrInterfaceDeclaration cls) {
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
