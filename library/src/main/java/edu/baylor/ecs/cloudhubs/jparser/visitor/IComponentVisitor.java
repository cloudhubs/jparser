package edu.baylor.ecs.jparser.visitor;

import edu.baylor.ecs.jparser.component.IComponent;
import edu.baylor.ecs.jparser.component.context.AnalysisContext;
import edu.baylor.ecs.jparser.component.impl.AnnotationComponent;
import edu.baylor.ecs.jparser.component.impl.ClassComponent;
import edu.baylor.ecs.jparser.component.impl.DirectoryComponent;
import edu.baylor.ecs.jparser.component.impl.FieldComponent;
import edu.baylor.ecs.jparser.component.impl.InterfaceComponent;
import edu.baylor.ecs.jparser.component.impl.MethodInfoComponent;
import edu.baylor.ecs.jparser.component.impl.MethodParamComponent;
import edu.baylor.ecs.jparser.component.impl.ModuleComponent;

public interface IComponentVisitor extends IComponent {

    void visit(AnnotationComponent component);
    void visit(ClassComponent component);
    void visit(DirectoryComponent component);
    void visit(FieldComponent component);
    void visit(InterfaceComponent component);
    void visit(MethodInfoComponent component);
    void visit(MethodParamComponent component);
    void visit(ModuleComponent component);
    void visit(AnalysisContext component);

}
