package edu.baylor.ecs.ciljssa.component;

import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;

public interface IComponent {

    void accept(IComponentVisitor visitor);

}
