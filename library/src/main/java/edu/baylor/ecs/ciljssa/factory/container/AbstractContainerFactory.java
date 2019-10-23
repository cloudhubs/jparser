package edu.baylor.ecs.ciljssa.factory.container;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.MetaSubComponent;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.factory.methodinfo.MethodInfoFactory;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: More of a container factory isn't it
 */
public abstract class AbstractContainerFactory implements IContainerFactory {

    private Long idEnumerator = 0L;

    protected Long getId() {
        idEnumerator++;
        return idEnumerator;
    }

    private Component createMetaSubComponent(Component parent, List<Component> methods,
                                             List<Component> constructors,
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

    protected List<Component> createMetaSubComponentAsList(Component parent, List<Component> methods,
                                                      List<Component> constructors,
                                                      List<AnnotationComponent> annotations,
                                                      List<ClassComponent> subClasses) {
        List<Component> output = new ArrayList<>();
        output.add(createMetaSubComponent(parent, methods, constructors, annotations, subClasses));
        return output;
    }

    protected ContainerStereotype createStereotype(ClassOrInterfaceDeclaration cls) {
        return ContainerStereotype.FABRICATED;
    }

    protected List<Component> createMethods(ClassOrInterfaceDeclaration cls, ContainerComponent parent) {
        List<Component> mds = new ArrayList<>();
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
    protected List<Component> createConstructors(ClassOrInterfaceDeclaration cls, Component parent) {
        List<Component> mds = new ArrayList<>();
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
        return AnnotationFactory.createAnnotationComponents(cls.getAnnotations());
    }

}
