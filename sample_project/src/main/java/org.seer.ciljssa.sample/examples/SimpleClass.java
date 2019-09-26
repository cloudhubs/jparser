package org.seer.ciljssa.examples;

import lombok.Data;

@Data
public class SimpleClass {

    private String q;
    private String w;

    public SimpleClass(int x, int y, int z) {
        q = "" + x + y + z;
    }

    public class SimpleInnerClass{
        public SimpleInnerClass() {

        }
    }

    public String getQ() {
        return q;
    }

    public void setQ(String g) {
        this.q = g;
    }

}
