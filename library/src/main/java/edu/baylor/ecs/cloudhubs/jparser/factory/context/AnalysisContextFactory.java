package edu.baylor.ecs.cloudhubs.jparser.factory.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.cloudhubs.jparser.builder.AnalysisContextBuilder;
import edu.baylor.ecs.cloudhubs.jparser.component.Component;
import edu.baylor.ecs.cloudhubs.jparser.component.context.JSSAContext;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.ClassComponent;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.DirectoryComponent;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.InterfaceComponent;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.ModuleComponent;
import edu.baylor.ecs.cloudhubs.jparser.component.context.AnalysisContext;

import edu.baylor.ecs.cloudhubs.jparser.factory.container.impl.ClassComponentFactory;
import edu.baylor.ecs.cloudhubs.jparser.factory.container.impl.InterfaceComponentFactory;
import edu.baylor.ecs.cloudhubs.jparser.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.cloudhubs.jparser.factory.directory.DirectoryFactory;
import edu.baylor.ecs.cloudhubs.jparser.model.InstanceType;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class AnalysisContextFactory {

    private ModuleComponentFactory moduleFactory;

    private static final boolean DEFAULT_FLAG = true;

    /**
     * If no flag is set when constructing, the default is true
     */
    public AnalysisContextFactory() {
        this(DEFAULT_FLAG); // defaults to true
    }

    /**
     * Creates an AnalysisContextFactory flagging whether the idEnumerators should be reset in dependent factories.
     * @param flag true for reset, false for keep the same
     */
    public AnalysisContextFactory(boolean flag) {
        this.moduleFactory = (ModuleComponentFactory) ModuleComponentFactory.getInstance();
        if (flag) {
            this.moduleFactory.resetIdEnumerator();
            ClassComponentFactory.getInstance().resetIdEnumerator();
            InterfaceComponentFactory.getInstance().resetIdEnumerator();
        }
    }

    public AnalysisContext createAnalysisContextFromFile(File file) {
        Component fileDirectory = new DirectoryFactory().createDirectoryGraphOfFile(file);
        return createAnalysisContextFromDirectoryGraph((DirectoryComponent) fileDirectory);
    }

    public AnalysisContext createAnalysisContextFromDirectoryGraph(Component inp) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpRequest.BodyPublisher publisher =
                HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(inp));
            var request = HttpRequest.newBuilder(URI.create("http://parser:8080/ctx"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("POST", publisher).build();
            AnalysisContext ctx = HttpClient.newHttpClient()
                .send(request, new JsonBodyHandler<>(AnalysisContext.class))
                .body();
            return ctx;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        DirectoryComponent root;
//        if (inp instanceof DirectoryComponent) {
//            root = (DirectoryComponent) inp;
//        } else {
//            return null;
//        }
//        AnalysisContext context = new AnalysisContext();
//        List<ModuleComponent> modules = createModulesFromDirectory(root, context);
//        List<String> classNames = modules.stream().map(ModuleComponent::getClassNames)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<String> interfaceNames = modules.stream().map(ModuleComponent::getInterfaceNames)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<ClassComponent> classes = modules.stream().map(ModuleComponent::getClasses)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<InterfaceComponent> interfaces = modules.stream().map(ModuleComponent::getInterfaces)
//                .flatMap(List::stream).collect(Collectors.toList());
//        Map<ModuleComponent, String> packageMap = modules.stream()
//                .collect(Collectors.toMap(p -> p, ModuleComponent::getPackageName, (p1, p2)->p1)); // Merge
//        List<Component> cls = modules.stream().map(ModuleComponent::getClassesAndInterfaces)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<ClassOrInterfaceDeclaration> clsd = modules.stream().map(ModuleComponent::getClassOrInterfaceDeclarations)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<MethodDeclaration> mds = modules.stream().map(ModuleComponent::getMethodDeclarations)
//                .flatMap(List::stream).collect(Collectors.toList());
//        List<Component> methods = modules.stream().map(ModuleComponent::getMethods)
//                .flatMap(List::stream).collect(Collectors.toList());
//        context = new AnalysisContextBuilder()
//                .withModules(modules)
//                .withClassNames(classNames)
//                .withClassesAndInterfaces(cls)
//                .withClassOrInterfaceDeclarations(clsd)
//                .withInterfaceNames(interfaceNames)
//                .withMethodDeclarations(mds)
//                .withMethods(methods)
//                .withRootPath(root.getPath())
//                .withDirectoryGraph(root)
//                .withPackageMap(packageMap)
//                .withClasses(classes)
//                .withInterfaces(interfaces)
//                .withInstanceType(InstanceType.ANALYSISCOMPONENT)
//                .build();
//        return context;
    }

    private List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc, Component parent) {
        List<ModuleComponent> list = new ArrayList<>();
        ModuleComponent module = new ModuleComponent();
        if (doc.getNumFiles() > 0) {
            // create parent directory
            module = moduleFactory.createComponent(parent, doc);
            module.setParent(parent);
            list.add(module);
        }
        for (DirectoryComponent e : doc.getSubDirectories()) {
            List<ModuleComponent> comps = createModulesFromDirectory(e, module);
            if (!comps.isEmpty()) {
                module.setSubModules(comps);
                list.addAll(comps);
            }
        }
        return list;
    }

    private List<ModuleComponent> createModules(List<ClassOrInterfaceDeclaration> cls) {
        List<ModuleComponent> modules = new ArrayList<>();
        return modules;
    }

}

class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

    private Class<W> wClass;

    public JsonBodyHandler(Class<W> wClass) {
        this.wClass = wClass;
    }

    @Override
    public HttpResponse.BodySubscriber<W> apply(HttpResponse.ResponseInfo responseInfo) {
        return asJSON(wClass);
    }

    public static <T> HttpResponse.BodySubscriber<T> asJSON(Class<T> targetType) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
            upstream,
            (String body) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(body, targetType);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
    }
}