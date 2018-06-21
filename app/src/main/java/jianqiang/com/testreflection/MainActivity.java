package jianqiang.com.testreflection;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class MainActivity extends Activity {

    //todo
    //静态方法: invoke(null)

    Button btnNormal;
    Button btnHook;
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = (TextView) findViewById(R.id.txtShow);


        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //测试Class
                test1();

                //测试ctor
                test2();
                test2_1();

                //测试method
                test3();
                test3_1();

                //测试field
                test4();
                test4_1();

                //测试Singleton
                AMN.getDefault().doSomething();
                test5();
                AMN.getDefault().doSomething();
            }
        });

        btnHook = (Button) findViewById(R.id.btnHook);
        btnHook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //1.获取Class类
    public void test1() {
        //通过getClass，每个Class都有这个函数
        String str = "abc";
        Class c1 = str.getClass();

        //这种方式最常见，通过静态方法Class.forName()
        try {
            Class c2 = Class.forName("java.lang.String");
            Class c3 = Class.forName("android.widget.Button");

            //通过getSuperClass，每个Class都有这个函数
            Class c5 = c3.getSuperclass();  //得到TextView
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //通过.class属性
        Class c6 = String.class;
        Class c7 = java.lang.String.class;
        Class c8 = MainActivity.InnerClass.class;
        Class c9 = int.class;
        Class c10 = int[].class;

        //基本类型包装类的TYPE语法
        Class c11 = Boolean.TYPE;
        Class c12 = Byte.TYPE;
        Class c13 = Character.TYPE;
        Class c14 = Short.TYPE;
        Class c15 = Integer.TYPE;
        Class c16 = Long.TYPE;
        Class c17 = Float.TYPE;
        Class c18 = Double.TYPE;
        Class c19 = Void.TYPE;

    }

    //2.1.获取类的构造函数，测试类TestClassCtor
    public void test2() {
        TestClassCtor r = new TestClassCtor();
        Class temp = r.getClass();
        String className = temp.getName();        // 获取指定类的类名

        Log.v("baobao", "获取类的所有ctor，不分public还是private----------------------------------------------");
        //获取类的所有ctor，不分public还是private
        try {
            Constructor[] theConstructors = temp.getDeclaredConstructors();        // 获取指定类的公有构造方法

            for (int i = 0; i < theConstructors.length; i++) {
                int mod = theConstructors[i].getModifiers();    // 输出修饰域和方法名称
                Log.v("baobao", Modifier.toString(mod) + " " + className + "(");

                Class[] parameterTypes = theConstructors[i].getParameterTypes();    // 获取指定构造方法的参数的集合
                for (int j = 0; j < parameterTypes.length; j++) {    // 输出打印参数列表
                    Log.v("baobao", parameterTypes[j].getName());
                    if (parameterTypes.length > j + 1) {
                        Log.v("baobao", ", ");
                    }
                }
                Log.v("baobao", ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("baobao", "获取类的所有public ctor----------------------------------------------");
        //获取类的所有public ctor
        try {
            Constructor[] theConstructors = temp.getConstructors();        // 获取指定类的所有构造方法

            for (int i = 0; i < theConstructors.length; i++) {
                int mod = theConstructors[i].getModifiers();    // 输出修饰域和方法名称
                Log.v("baobao", Modifier.toString(mod) + " " + className + "(");

                Class[] parameterTypes = theConstructors[i].getParameterTypes();    // 获取指定构造方法的参数的集合
                for (int j = 0; j < parameterTypes.length; j++) {    // 输出打印参数列表
                    Log.v("baobao", parameterTypes[j].getName());
                    if (parameterTypes.length > j + 1) {
                        Log.v("baobao", ", ");
                    }
                }
                Log.v("baobao", ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("baobao", "获取类的某个ctor----------------------------------------------");

        //获取类的某个ctor
        try {
            //获取类的ctor：TestClassCtor()
            Constructor c1 = temp.getDeclaredConstructor();             //public
            printCtor(c1);
            Constructor c1_1 = temp.getConstructor();   //不分public和非public
            printCtor(c1_1);

            //获取类的ctor：TestClassCtor(int a)
            Class[] p2 = {int.class};
            Constructor c2 = temp.getDeclaredConstructor(p2);
            printCtor(c2);

            //获取类的ctor：TestClassCtor(int a, String b)
            Class[] p3 = {int.class, String.class};
            Constructor c3 = temp.getDeclaredConstructor(p3);
            printCtor(c3);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    void printCtor(Constructor theConstructor) {
        int mod = theConstructor.getModifiers();    // 输出修饰域和方法名称
        Log.v("baobao", Modifier.toString(mod)  + "(");

        Class[] parameterTypes = theConstructor.getParameterTypes();    // 获取指定构造方法的参数的集合
        for (int j = 0; j < parameterTypes.length; j++) {    // 输出打印参数列表
            Log.v("baobao", parameterTypes[j].getName());
            if (parameterTypes.length > j + 1) {
                Log.v("baobao", ", ");
            }
        }
        Log.v("baobao", ")");
    }

    public void test2_1() {
        //通过反射，获取一个类的ctor，然后调用它
        try {
            Class r = Class.forName("jianqiang.com.testreflection.TestClassCtor");

            //含参
            Class[] p3 = {int.class, String.class};
            Constructor ctor = r.getDeclaredConstructor(p3);
            Object obj = ctor.newInstance(1, "bjq");

            //无参
            Constructor ctor2 = r.getDeclaredConstructor();
            Object obj2 = ctor2.newInstance();

            //也可以使用Class的newInstance方法，但Class仅提供默认无参的实例化方法
            Object obj4 = r.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.1.获取类的private实例方法，调用它
    public void test3() {
        try {
            //以下4句话，创建一个对象
            Class r = Class.forName("jianqiang.com.testreflection.TestClassCtor");
            Class[] p3 = {int.class, String.class};
            Constructor ctor = r.getDeclaredConstructor(p3);
            Object obj = ctor.newInstance(1, "bjq");

            //以下4句话，调用一个private方法
            Class[] p4 = {String.class};
            Method method = r.getDeclaredMethod("doSOmething", p4); //在指定类中获取指定的方法
            method.setAccessible(true);

            Object argList[] = {"jianqiang"};   //这里写死，下面有个通用的函数getMethodParamObject
            Object result = method.invoke(obj, argList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.2.获取类的private静态方法，调用它
    public void test3_1() {
        try {
            //以下4句话，创建一个对象
            Class r = Class.forName("jianqiang.com.testreflection.TestClassCtor");

            //以下3句话，调用一个private静态方法
            Method method = r.getDeclaredMethod("work"); //在指定类中获取指定的方法
            method.setAccessible(true);
            method.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //4.1.获取类的private实例字段，修改它
    public void test4() {
        try {
            //以下4句话，创建一个对象
            Class r = Class.forName("jianqiang.com.testreflection.TestClassCtor");
            Class[] p3 = {int.class, String.class};
            Constructor ctor = r.getDeclaredConstructor(p3);
            Object obj = ctor.newInstance(1, "bjq");

            //获取name字段，private
            Field field = r.getDeclaredField("name");
            field.setAccessible(true);

            Object fieldObject = field.get(obj);

            //只对obj有效
            field.set(obj, "jianqiang1982");

            TestClassCtor testClassCtor = new TestClassCtor(100);
            testClassCtor.getName(); //仍然返回null，并没有修改

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取类的private静态字段，修改它
    public void test4_1() {
        try {
            //以下4句话，创建一个对象
            Class r = Class.forName("jianqiang.com.testreflection.TestClassCtor");

            //获取address静态字段，private
            Field field = r.getDeclaredField("address");
            field.setAccessible(true);

            Object fieldObject = field.get(null);

            field.set(fieldObject, "ABCD");

            //静态变量，一次修改，终生受用
            TestClassCtor.printAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取参数Object[]
    public Object[] getMethodParamObject(String[] types, String[] params) {

        Object[] retObjects = new Object[params.length];

        for (int i = 0; i < retObjects.length; i++) {
            if(!params[i].trim().equals("")||params[i]!=null){
                if(types[i].equals("int")||types[i].equals("Integer")){
                    retObjects[i]= new Integer(params[i]);
                }
                else if(types[i].equals("float")||types[i].equals("Float")){
                    retObjects[i]= new Float(params[i]);
                }
                else if(types[i].equals("double")||types[i].equals("Double")){
                    retObjects[i]= new Double(params[i]);
                }
                else if(types[i].equals("boolean")||types[i].equals("Boolean")){
                    retObjects[i]=new Boolean(params[i]);
                }
                else{
                    retObjects[i] = params[i];
                }
            }
        }

        return retObjects;
    }

    class InnerClass {

    }


    public void test5() {

        ActivityManager am;
        try {
            // gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            Class<?> singleton = Class.forName("jianqiang.com.testreflection.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            //获取AMN的gDefault单例gDefault，gDefault是静态的
            Class<?> activityManagerNativeClass = Class.forName("jianqiang.com.testreflection.AMN");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            // AMN的gDefault对象里面原始的 B2对象
            Object rawB2Object = mInstanceField.get(gDefault);

            // 创建一个这个对象的代理对象ClassB2Mock, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> classB2Interface = Class.forName("jianqiang.com.testreflection.ClassB2Interface");
            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[] { classB2Interface },
                    new ClassB2Mock(rawB2Object));
            mInstanceField.set(gDefault, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}