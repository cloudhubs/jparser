package edu.baylor.ecs.ciljssa.visitor;

import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.FieldComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.MetaSubComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodParamComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;

public interface IComponentVisitor extends IComponent {

    void visit(AnnotationComponent component);
    void visit(ClassComponent component);
    void visit(DirectoryComponent component);
    void visit(FieldComponent component);
    void visit(InterfaceComponent component);
    void visit(MetaSubComponent component);
    void visit(MethodInfoComponent component);
    void visit(MethodParamComponent component);
    void visit(ModuleComponent component);

}
