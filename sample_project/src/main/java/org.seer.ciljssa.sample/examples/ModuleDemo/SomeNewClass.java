package org.seer.ciljssa.sample.examples.ModuleDemo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is just an example class with a sub class and interface definition for my own analysis purposes.
 */

@Data
public class SomeNewClass {

    protected static final int MAGICNUMBER = 10;

    private List<IExampleInterface> otherExamples;
    private String memberTwo;
    private double memberThree;

    public interface IExampleInterface {
        boolean interfaceMember = true;
        boolean interfaceExampleMethod();
    }

    public SomeNewClass() {
        this.otherExamples = new ArrayList<>();
        this.memberTwo = "Default value";
        this.memberThree = 0.0;
    }

    public String[] anotherExampleMethod(String x, int y, boolean z, double q) {
        String[] test = new String[3];
        test[0] = x;
        test[1] = someOtherThingMakingFunction(y) + "";
        test[2] = z ? q + "" : (q - 10) + "";
        return test;
    }

    private String someOtherThingMakingFunction(int x) {
        return x + " is a good number";
    }

    private int someFunction(int num) {
        anotherExampleMethod("string", 10, true, 10.4);
        return num *= 10000;
    }
}
