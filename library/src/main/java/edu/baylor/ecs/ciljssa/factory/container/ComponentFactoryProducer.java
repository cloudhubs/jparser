package edu.baylor.ecs.ciljssa.factory.container;

import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.container.impl.ClassComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.InterfaceComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

public class ComponentFactoryProducer {

    public static AbstractContainerFactory getFactory(ClassOrInterface coi) {
        switch(coi) {
            case CLASS: return ClassComponentFactory.getInstance();
            case INTERFACE: return InterfaceComponentFactory.getInstance();
            case MODULE: return ModuleComponentFactory.getInstance();
            default: return null;
        }
    }

}
