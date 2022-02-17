package day03.loader;

import java.io.IOException;

public class TestResolution {

    static class A {
        static {
            System.out.println("init A");
        }
    }

    static class B {
        static {
            System.out.println("init B");
        }
    }

    static class C {
        static {
            System.out.println("init C");
        }
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
        A a = new A();
        System.in.read();
        B b = new B();
        System.in.read();
        C c = new C();
        System.in.read();
    }

}
