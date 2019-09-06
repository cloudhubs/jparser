package org.seer.ciljssa.examples;

import lombok.Data;

import java.util.ArrayList;

/**
 * This class is just an example class with a sub class and interface definition for my own analysis purposes.
 */

@Data
public class ExampleClass {

    protected final int MAGICNUMBER = 10;

    private ArrayList<IExampleInterface> otherExamples;
    private SubClass memberOne;
    private String memberTwo;
    private double memberThree;

    public interface IExampleInterface {
        boolean interfaceMember = true;
        boolean interfaceExampleMethod();
    }

    @Data
    public class SubClass {

        int subMember;
        boolean altered = false;

        public SubClass() {
            this.subMember = MAGICNUMBER;
        }

        public SubClass(int subMember) {
            this.subMember = subMember;
        }

        public SubClass alter() {
            this.altered = true;
            return this;
        }

        @Deprecated
        private boolean exampleMethod(int x){
            return false;
        }
    }

    public ExampleClass() {
        this.otherExamples = new ArrayList<>();
        this.memberOne = new SubClass();
        this.memberTwo = "Default value";
        this.memberThree = 0.0;
    }

    public String[] anotherExampleMethod(String x, String y, boolean z, double q) {
        String[] test = new String[3];
        test[0] = x;
        test[1] = y;
        test[2] = z ? q + "" : (q - 10) + "";
        return test;
    }
}