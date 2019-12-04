/**
 * This class abstractly sets up necessary components for integration tests. Running any empty test extending this class
 * will inherently test initialization.
 */

//@RunWith(JUnit4.class)
public abstract class ImplementationTests {

    /**
     * This needs to be put on halt right now, I was temporarily using hard coded paths for creating the tests. Needs
     * relative paths or something that could work on any system.
     */
//    protected static final String DEFAULT_DIR_PATH = "/Users/simmonsjo/Documents/ciljssa/sample_project/src/main/java/org.seer.ciljssa.sample/examples";
//    protected DirectoryComponent defaultDirectory;
//
//    protected DirectoryFactory directoryFactory;
//    protected AnnotationFactory annotationFactory;
//    protected ClassComponentFactory classComponentFactory;
//    protected InterfaceComponentFactory interfaceComponentFactory;
//    protected ModuleComponentFactory moduleComponentFactory;
//    protected AnalysisContextFactory analysisContextFactory;
//    protected MethodInfoFactory methodInfoFactory;
//
//    protected ComponentFactoryProducer componentFactoryProducer;
//
//    protected ModuleComponent defaultModule;
//
//    @Before
//    public void init() {
//        directoryFactory = new DirectoryFactory("Java");
//        annotationFactory = new AnnotationFactory();
//        moduleComponentFactory = (ModuleComponentFactory) ModuleComponentFactory.getInstance();
//        analysisContextFactory = new AnalysisContextFactory();
//        methodInfoFactory = MethodInfoFactory.getInstance();
//
//        setUpDirectoryComponent();
//        setUpDefaultModule();
//
//        classComponentFactory = (ClassComponentFactory) ClassComponentFactory.getInstance();
//        interfaceComponentFactory = (InterfaceComponentFactory) InterfaceComponentFactory.getInstance();
//    }
//
//    protected void setUpDirectoryComponent() {
//        DirectoryComponent component = new DirectoryComponent(DEFAULT_DIR_PATH);
//        component.setHasSubDirectories(true);
//        component.setFiles(Arrays.stream(Objects.requireNonNull(new File(DEFAULT_DIR_PATH)
//                .listFiles())).filter(x -> x.getName().endsWith(".java")).collect(Collectors.toList()));
//        component.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
//        DirectoryComponent subComponent = new DirectoryComponent(DEFAULT_DIR_PATH + "/ModuleDemo");
//        subComponent.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
//        subComponent.setFiles(Arrays.stream(Objects.requireNonNull(new File("/Users/simmonsjo/Documents/ciljssa/sample_project/src/main/java/org.seer.ciljssa.sample/examples/ModuleDemo")
//                .listFiles())).filter(x -> x.getName().endsWith(".java")).collect(Collectors.toList()));
//        component.addSubDirectory(subComponent);
//        subComponent.setParent(component);
//        component.setLanguage("Java");
//        subComponent.setLanguage("Java");
//        this.defaultDirectory = component;
//    }
//
//    protected void setUpDefaultModule() {
//        ModuleComponent parent = new ModuleComponent();
//        parent.setInstanceName("PARENT");
//        this.defaultModule = moduleComponentFactory.createComponent(parent, defaultDirectory);
//        ModuleComponent defaultSub = moduleComponentFactory
//                .createComponent(defaultModule, defaultDirectory.getSubDirectories().get(0));
//        this.defaultModule.addSubComponent(defaultSub);
//    }

}
