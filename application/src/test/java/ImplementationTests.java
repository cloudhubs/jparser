import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.annotation.AnnotationFactory;
import edu.baylor.ecs.ciljssa.factory.container.ComponentFactoryProducer;
import edu.baylor.ecs.ciljssa.factory.container.impl.ClassComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.InterfaceComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
import edu.baylor.ecs.ciljssa.factory.directory.DirectoryFactory;
import edu.baylor.ecs.ciljssa.factory.methodinfo.MethodInfoFactory;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class abstractly sets up necessary components for integration tests. Running any empty test extending this class
 * will inherently test initialization.
 */

@RunWith(JUnit4.class)
public abstract class ImplementationTests {

    protected static final String DEFAULT_DIR_PATH = "/Users/simmonsjo/Documents/ciljssa/sample_project";
    protected DirectoryComponent defaultDirectory;

    protected DirectoryFactory directoryFactory;
    protected AnnotationFactory annotationFactory;
    protected ClassComponentFactory classComponentFactory;
    protected InterfaceComponentFactory interfaceComponentFactory;
    protected ModuleComponentFactory moduleComponentFactory;
    protected AnalysisContextFactory analysisContextFactory;
    protected MethodInfoFactory methodInfoFactory;

    protected ComponentFactoryProducer componentFactoryProducer;

    protected ModuleComponent defaultModule;

    @Before
    public void init() {
        directoryFactory = new DirectoryFactory("Java");
        annotationFactory = new AnnotationFactory();
        moduleComponentFactory = (ModuleComponentFactory) ModuleComponentFactory.getInstance();
        analysisContextFactory = new AnalysisContextFactory();
        methodInfoFactory = MethodInfoFactory.getInstance();

        setUpDirectoryComponent();
        setUpDefaultModule();

        classComponentFactory = (ClassComponentFactory) ClassComponentFactory.getInstance();
        interfaceComponentFactory = (InterfaceComponentFactory) InterfaceComponentFactory.getInstance();
    }

    protected void setUpDirectoryComponent() {
        DirectoryComponent component = new DirectoryComponent(DEFAULT_DIR_PATH);
        component.setHasSubDirectories(true);
        component.setFiles(Arrays.stream(Objects.requireNonNull(new File(DEFAULT_DIR_PATH)
                .listFiles())).filter(x -> x.getName().endsWith(".java")).collect(Collectors.toList()));
        component.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
        DirectoryComponent subComponent = new DirectoryComponent(DEFAULT_DIR_PATH + "/ModuleDemo");
        subComponent.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
        subComponent.setFiles(Arrays.stream(Objects.requireNonNull(new File("/Users/simmonsjo/Documents/ciljssa/sample_project/src/main/java/org.seer.ciljssa.sample/examples/ModuleDemo")
                .listFiles())).filter(x -> x.getName().endsWith(".java")).collect(Collectors.toList()));
        component.addSubDirectory(subComponent);
        subComponent.setParent(component);
        this.defaultDirectory = component;
    }

    protected void setUpDefaultModule() {
        ModuleComponent parent = new ModuleComponent();
        parent.setInstanceName("PARENT");
        this.defaultModule = moduleComponentFactory.createComponent(parent, defaultDirectory);
        ModuleComponent defaultSub = moduleComponentFactory
                .createComponent(defaultModule, defaultDirectory.getSubDirectories().get(0));
        this.defaultModule.addSubComponent(defaultSub);
    }

}
