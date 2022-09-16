package day03.loader;

import java.io.IOException;
import day03.loader.Student;

/**
 * 此案例说明
 <ul>
    <li>类加载是懒惰的, 首次用到时才加载（下面的初始化条件满足也会导致类加载）
      <ol>
         <li>使用了类.class</li>
         <li>用类加载器的 loadClass 方法加载类</li>
      </ol>
    </li>
    <li>类初始化是懒惰的, 满足条件有
      <ol>
         <li>main 方法所在类</li>
         <li>首次访问静态方法或静态变量(非 final, 或 final的 引用类型)</li>
         <li>子类初始化, 导致的父类初始化</li>
         <li>Class.forName(类名, true, loader) 或 Class.forName(类名)</li>
         <li>new, clone, 反序列化时</li>
      </ol>
    </li>
 </ul>

 */
public class TestLazy {
    private Class<?> studentClass;
    public static void main(String[] args) throws IOException {
        System.out.println("未用到 Student");
        System.in.read();

        System.out.println(Student.class);   // 关键代码1，会触发类加载
        System.out.println("已加载 Student");
        TestLazy testLazy = new TestLazy();
        testLazy.studentClass = Student.class;
        System.in.read();

        Student stu = new Student();        // 关键代码2，会触发类初始化
        System.out.println("已初始化 Student");
        System.in.read();
    }
}
