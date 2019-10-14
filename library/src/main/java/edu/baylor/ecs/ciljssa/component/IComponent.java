package edu.baylor.ecs.ciljssa.component;

import edu.baylor.ecs.ciljssa.model.InstanceType;

public interface IComponent {

    String getPathToComponent();
    void setPathToComponent(String path);
    String getPackageName();
    void setPackageName(String name);
    String getInstanceName();
    void setInstanceName(String name);
    InstanceType getInstanceType();
    void setInstanceType(InstanceType type);
    IComponent getParentComponent();
    void setParentComponent(IComponent component);

}
