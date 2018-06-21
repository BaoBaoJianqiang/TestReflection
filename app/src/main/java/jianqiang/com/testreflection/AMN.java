package jianqiang.com.testreflection;

public class AMN {

    private static final Singleton<ClassB2Interface> gDefault = new Singleton<ClassB2Interface>() {
        protected ClassB2Interface create() {
            ClassB2 b2 = new ClassB2();
            b2.id = 2;
            return b2;
        }
    };

    static public ClassB2Interface getDefault() {
        return gDefault.get();
    }
}
