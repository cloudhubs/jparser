package edu.baylor.ecs.ciljssa.factory.container;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
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
public abstract class AbstractComponentFactory implements IComponentFactory {

    private Long idEnumerator = 0L;

    protected Long getId() {
        idEnumerator++;
        return idEnumerator;
    }

    protected MetaSubComponent createMetaSubComponent(IComponent parent, List<MethodInfoComponent> methods,
                                                            List<MethodInfoComponent> constructors,
                                                            List<AnnotationComponent> annotations,
                                                            List<ClassComponent> subClasses) {
        MetaSubComponent component = new MetaSubComponent();
        component.setMethodInfoComponents(methods);
        component.setConstructorComponents(constructors);
        component.setAnnotationComponents(annotations);
        component.setSubClassComponents(subClasses);
        component.setInstanceType(InstanceType.META);
        component.setParentComponent(parent);
        component.setInstanceName(parent.getInstanceName() + "::MetaSubComponent");
        component.setPathToComponent(parent.getPathToComponent());
        component.setPackageName(parent.getPackageName());
        return component;
    }

    protected ContainerStereotype createStereotype(ClassOrInterfaceDeclaration cls) {
        /**
         * TODO: Make stereotypes
         */
        return ContainerStereotype.FABRICATED;
    }

    protected List<MethodInfoComponent> createMethods(ClassOrInterfaceDeclaration cls, IContainerComponent parent) {
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
    protected List<MethodInfoComponent> createConstructors(ClassOrInterfaceDeclaration cls, IComponent parent) {
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
        List<AnnotationComponent> annotations = new ArrayList<>();
        for (AnnotationExpr exp : cls.getAnnotations()) {
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
