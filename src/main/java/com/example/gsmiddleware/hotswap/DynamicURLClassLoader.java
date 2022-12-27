package com.example.gsmiddleware.hotswap;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yangzq80@gmail.com
 * @date 11/18/22
 */
public class DynamicURLClassLoader extends URLClassLoader {


    public DynamicURLClassLoader(URL[] urls) {
        super(urls);
    }

    public DynamicURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
}
