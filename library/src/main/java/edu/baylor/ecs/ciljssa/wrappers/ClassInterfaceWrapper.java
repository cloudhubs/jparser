
//TODO: This class is deprecated and will be removed and replaced by 3 classes:
//      ComponentWrapper <- (ClassWrapper, InterfaceWrapper)

package edu.baylor.ecs.ciljssa.wrappers;

@Deprecated
public class ClassInterfaceWrapper {

}

//package edu.baylor.ecs.ciljssa.wrappers;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.body.*;
//import com.github.javaparser.ast.expr.AnnotationExpr;
//import com.github.javaparser.ast.expr.MethodCallExpr;
//import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class ClassInterfaceWrapper {
//
//    enum ClassOrInterface {
//        Class, Interface
//    }
//
//    @JsonIgnore
//    private ClassOrInterfaceDeclaration cls;
//    @JsonIgnore
//    private CompilationUnit analysisUnit;
//    @JsonIgnore
//    private List<MethodDeclaration> methodDeclarations;
//
//
//    @JsonProperty(value = "declaration_type")
//    private String classOrInterface;
//    @JsonProperty(value = "name")
//    private String instanceName;
//    private MethodInfoWrapper[] methods;
//    private MethodInfoWrapper[] constructors;
//
//    public ClassInterfaceWrapper(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
//        this.analysisUnit = unit;
//        this.cls = cls;
//        this.classOrInterface = isClass() ? ClassOrInterface.Class.toString() : ClassOrInterface.Interface.toString();
//        this.instanceName = cls.getNameAsString();
//        this.constructors = createConstructors(cls.getConstructors().toArray(new ConstructorDeclaration[0]));
//        this.methods = createMethodInfoWrappers(cls.getMethods().toArray(new MethodDeclaration[0]));
//    }
//
//    @JsonIgnore
//    public boolean isClass() {
//        return cls.isClassOrInterfaceDeclaration() && !cls.isInterface();
//    }
//
//    @JsonIgnore
//    public boolean isInterface() {
//        return cls.isInterface();
//    }
//
////    private MethodInfoWrapper[] createSubMethodCalls(MethodDeclaration method) {
////        List<MethodInfoWrapper> subCalls = new ArrayList<>();
////        method.accept(new VoidVisitorAdapter<Object>() {
////            @Override
////            public void visit(MethodCallExpr n, Object arg) {
////                super.visit(n, arg);
////                subCalls.add(createMethodInfoWrapperFromExpr(n));
////            }
////        }, null);
////        return subCalls.toArray(new MethodInfoWrapper[0]);
////    }
//
//    private MethodInfoWrapper[] createConstructors(ConstructorDeclaration[] constructors) {
//        List<MethodInfoWrapper> mds = new ArrayList<>();
//        Arrays.stream(constructors).forEach(obj -> {
//            ConstructorDeclaration cd = (ConstructorDeclaration) obj;
//
//            MethodInfoWrapper md = new MethodInfoWrapper();
//            md.setAnnotations(initAnnotations(cd.getAnnotations()));
//            md.setMethodParams(initParameters(cd.getParameters()));
//
//            md.setAccessor(cd.getAccessSpecifier().asString());
//            md.setMethodName(cd.getNameAsString());
//           // md.setSubMethods(createSubMethodCalls(cd.toMethodDeclaration().get()));
//            //md.setSubMethods();
//
//            mds.add(md);
//        });
//        return mds.toArray(new MethodInfoWrapper[0]);
//    }
//
//    private List<AnnotationWrapper> initAnnotations(List<AnnotationExpr> list) {
//        List<AnnotationWrapper> annotations = new ArrayList<>();
//        for (AnnotationExpr exp : list) {
//            AnnotationWrapper y = new AnnotationWrapper();
//            y.setAnnotation(exp);
//            y.setAnnotationMetaModel(exp.getMetaModel().toString());
//            y.setAsString(exp.getName().asString());
//            y.setMetaModelFieldName(exp.getMetaModel().getMetaModelFieldName());
//            annotations.add(y);
//        }
//        return annotations;
//    }

//}
