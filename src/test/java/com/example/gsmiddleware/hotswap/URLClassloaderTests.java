package com.example.gsmiddleware.hotswap;

import com.example.gsmiddleware.hotswap.DynamicURLClassLoader;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yangzq80@gmail.com
 * @date 11/18/22
 */
public class URLClassloaderTests {
    @Test
    void TestClassloader() throws Exception {

//        URL url = new URL("file:/Users/zqy/test/classes/");

        URL url = new URL("http://localhost:8088/");
        DynamicURLClassLoader classLoader = new DynamicURLClassLoader(new URL[]{url});

        Class<?> thisClass = classLoader.loadClass("com.example.Test");

        System.out.println(thisClass.getMethods()[0].getName());
        System.out.println(thisClass.getClassLoader());
        classLoader.close();
    }
}
