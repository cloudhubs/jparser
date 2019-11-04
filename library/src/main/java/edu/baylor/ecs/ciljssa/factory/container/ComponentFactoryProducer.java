package edu.baylor.ecs.ciljssa.factory.container;

import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.container.impl.ClassComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.InterfaceComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

public class ComponentFactoryProducer {

    public static AbstractContainerFactory getFactory(ClassOrInterface coi, ModuleComponent module) {
        switch(coi) {
            case CLASS: return new ClassComponentFactory(module);
            case INTERFACE: return new InterfaceComponentFactory(module);
            case MODULE: return new ModuleComponentFactory();
            default: return null;
        }
    }

}