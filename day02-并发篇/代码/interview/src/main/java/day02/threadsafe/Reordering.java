package day02.threadsafe;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

// 有序性例子
// java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t day02.threadsafe.Reordering.Case1
// java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t day02.threadsafe.Reordering.Case2
// java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -jar jcstress.jar -t day02.threadsafe.Reordering.Case3
public class Reordering {
    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "INTERESTING")
    @State
    public static class Case1 {
        int x;
        int y;

        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }

    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "FORBIDDEN")
    @State
    public static class Case2 {
        int x;
        volatile int y;

        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }

    @JCStressTest
    @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
    @Outcome(id = "1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "ACCEPTABLE_INTERESTING")
    @State
    public static class Case3 {
        volatile int x;
        int y;

        @Actor
        public void actor1() {
            x = 1;
            y = 1;
        }

        @Actor
        public void actor2(II_Result r) {
            r.r1 = y;
            r.r2 = x;
        }
    }
}