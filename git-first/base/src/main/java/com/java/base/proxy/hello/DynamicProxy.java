package com.java.base.proxy.hello;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Slf4j
public class DynamicProxy {

    public static void main(String[] args) {

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args){
                // 代理类的内部操作
                log.info("method:{}", method.getName());
                if("name".equals(method.getName())){
                    log.info("request name method");
                }else if ("setWorld".equals(method.getName())){
                    log.info("request setWorld method");
                }else{
                    log.info("request ===");
                }
                log.info("params:{}", Arrays.toString(args));
                return null;
            }
        };

        // 代理对象
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                Hello.class.getInterfaces(), // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler
        // 使用代理对象调用方法
        hello.name("hah");

        World world = (World)Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[]{Hello.class, World.class}, // 传入要实现的接口
                handler);  // 传入处理调用方法的InvocationHandler

        world.setWorld();
        world.setWorld1("hha", 12, "01-01");

    }

    @Test
    public void proxy(){
        this.helloProxy();
    }

    private void helloProxy(){
        InvocationHandler handler = (proxy, method, args) -> {
            log.info("method:{}", method.getName());
            if("name".equals(method.getName())){
                log.info("request name method");
            }else{
                log.info("request getName method");
            }
            return null;
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[] { Hello.class }, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler

        hello.getName();
    }

}
