package edu.baylor.ecs.cloudhubs.jparser.component;

import edu.baylor.ecs.cloudhubs.jparser.visitor.IComponentVisitor;

public interface IComponent {

    void accept(IComponentVisitor visitor);

}
