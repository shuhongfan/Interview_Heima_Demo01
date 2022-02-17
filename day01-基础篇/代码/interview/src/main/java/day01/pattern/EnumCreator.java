package day01.pattern;

import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

// 示例：通过 Unsafe 造出一个 Enum 对象
public class EnumCreator {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        long nameOffset = unsafe.objectFieldOffset(Enum.class.getDeclaredField("name"));
        long ordinalOffset = unsafe.objectFieldOffset(Enum.class.getDeclaredField("ordinal"));
        Sex o = (Sex) unsafe.allocateInstance(Sex.class);
        unsafe.compareAndSwapObject(o, nameOffset, null, "阴阳人");
        unsafe.compareAndSwapInt(o, ordinalOffset, 0, 2);
        System.out.println(o.name());
        System.out.println(o.ordinal());
    }

}
