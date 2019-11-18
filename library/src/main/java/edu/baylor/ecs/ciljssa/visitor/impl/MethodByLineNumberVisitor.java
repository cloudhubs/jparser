package edu.baylor.ecs.ciljssa.visitor.impl;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.FieldComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodParamComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EXPERIMENTAL
 */
@Deprecated
public class MethodByLineNumberVisitor implements IComponentVisitor {

    private MethodInfoComponent method;
    private int lineNumber;

    public MethodByLineNumberVisitor(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public void visit(InterfaceComponent component) {
        Optional<Component> result = component.getMethods().stream()
                .filter(x -> ((MethodInfoComponent) x).getLineBegin() == lineNumber).findFirst();
        result.ifPresent(value -> this.method = (MethodInfoComponent) value);
    }

    @Override
    public void visit(ClassComponent component) {
        Optional<Component> result = component.getMethods().stream()
                .filter(x -> ((MethodInfoComponent) x).getLineBegin() == lineNumber).findFirst();
        result.ifPresent(value -> this.method = (MethodInfoComponent) value);
    }

    @Override
    public void visit(MethodInfoComponent component) {
        if (component.getLineBegin() == this.lineNumber) {
            this.method = component;
        }
    }

    @Override
    public void visit(ModuleComponent component) {
        Optional<Component> result = component.getMethods().stream()
                .filter(x -> ((MethodInfoComponent) x).getLineBegin() == lineNumber).findFirst();
        result.ifPresent(value -> this.method = (MethodInfoComponent) value);
    }

    @Override
    public void visit(AnalysisContext component) {

    }

    @Override
    public void visit(AnnotationComponent component) {
    }

    @Override
    public void visit(DirectoryComponent component) {
    }

    @Override
    public void visit(FieldComponent component) {
    }

    @Override
    public void visit(MethodParamComponent component) {
    }

    @Override
    public void accept(IComponentVisitor visitor) {
    }
}
