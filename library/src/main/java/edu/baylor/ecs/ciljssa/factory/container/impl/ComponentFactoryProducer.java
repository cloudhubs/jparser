package edu.baylor.ecs.ciljssa.factory.container.impl;

import edu.baylor.ecs.ciljssa.factory.container.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

public class ComponentFactoryProducer {

    public static AbstractComponentFactory getFactory(ClassOrInterface coi) {
        if (coi == ClassOrInterface.CLASS) {
            return new ClassComponentFactory();
        } else {
            return new InterfaceComponentFactory();
        }
    }

}
