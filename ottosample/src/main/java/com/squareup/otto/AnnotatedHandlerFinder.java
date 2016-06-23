/*
 * Copyright (C) 2012 Square, Inc.
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.otto;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Helper methods for finding methods annotated with {@link Produce} and {@link Subscribe}.
 *
 * @author Cliff Biffle
 * @author Louis Wasserman
 * @author Jake Wharton
 */
final class AnnotatedHandlerFinder {

  /** Cache event bus producer methods for each class.
   *  缓存 @Produce 注解方法
   * */
  private static final ConcurrentMap<Class<?>, Map<Class<?>, Method>> PRODUCERS_CACHE =
    new ConcurrentHashMap<Class<?>, Map<Class<?>, Method>>();

  /** Cache event bus subscriber methods for each class.
   * 缓存 @Subscribe 方法
   * */
  private static final ConcurrentMap<Class<?>, Map<Class<?>, Set<Method>>> SUBSCRIBERS_CACHE =
    new ConcurrentHashMap<Class<?>, Map<Class<?>, Set<Method>>>();

  private static void loadAnnotatedProducerMethods(Class<?> listenerClass,
      Map<Class<?>, Method> producerMethods) {
    Map<Class<?>, Set<Method>> subscriberMethods = new HashMap<Class<?>, Set<Method>>();
    loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
  }

  private static void loadAnnotatedSubscriberMethods(Class<?> listenerClass,
      Map<Class<?>, Set<Method>> subscriberMethods) {
    Map<Class<?>, Method> producerMethods = new HashMap<Class<?>, Method>();
    loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
  }

  /**
   * Load all methods annotated with {@link Produce} or {@link Subscribe} into their respective caches for the
   * specified class.
   * 读取目标类中的所有 @Produce 和 @Subscribe 方法
   */
  private static void loadAnnotatedMethods(Class<?> listenerClass,
      Map<Class<?>, Method> producerMethods, Map<Class<?>, Set<Method>> subscriberMethods) {
    for (Method method : listenerClass.getDeclaredMethods()) {
      // The compiler sometimes creates synthetic bridge methods as part of the
      // type erasure process. As of JDK8 these methods now include the same
      // annotations as the original declarations. They should be ignored for
      // subscribe/produce.
      if (method.isBridge()) {
        continue;
      }
      // 如果方法标注是Subscribe
      if (method.isAnnotationPresent(Subscribe.class)) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 参数不等于1个 ，throw exception
        if (parameterTypes.length != 1) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires "
              + parameterTypes.length + " arguments.  Methods must require a single argument.");
        }

        // 参数为接口，抛出异常
        Class<?> eventType = parameterTypes[0];
        if (eventType.isInterface()) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + eventType
              + " which is an interface.  Subscription must be on a concrete class type.");
        }

        // 方法不为public，抛出异常
        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
          throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + eventType
              + " but is not 'public'.");
        }

        // 把 @Subscribe 的方法名放到set 中
        Set<Method> methods = subscriberMethods.get(eventType);
        if (methods == null) {
          methods = new HashSet<Method>();
          subscriberMethods.put(eventType, methods);
        }
        methods.add(method);
      } else if (method.isAnnotationPresent(Produce.class)) {
        // 方法名为 @Produce
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 参数不为0 ，抛出异常
        if (parameterTypes.length != 0) {
          throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires "
              + parameterTypes.length + " arguments.  Methods must require zero arguments.");
        }
        // 返回的参数为 void，抛出异常。正常是某个event
        if (method.getReturnType() == Void.class) {
          throw new IllegalArgumentException("Method " + method
              + " has a return type of void.  Must declare a non-void type.");
        }

        Class<?> eventType = method.getReturnType();
        // 返回值不能是接口
        if (eventType.isInterface()) {
          throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + eventType
              + " which is an interface.  Producers must return a concrete class type.");
        }

        // 返回值不能为Void
        if (eventType.equals(Void.TYPE)) {
          throw new IllegalArgumentException("Method " + method + " has @Produce annotation but has no return type.");
        }

        // 方法必须是public
        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
          throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + eventType
              + " but is not 'public'.");
        }

        // 如果已经保存相同返回类型的 @Produce 方法，返回异常
        if (producerMethods.containsKey(eventType)) {
          throw new IllegalArgumentException("Producer for type " + eventType + " has already been registered.");
        }
        producerMethods.put(eventType, method);
      }
    }

    // 缓存所有的 @Produce 和 @Subscribe 方法
    PRODUCERS_CACHE.put(listenerClass, producerMethods);
    SUBSCRIBERS_CACHE.put(listenerClass, subscriberMethods);
  }

  /** This implementation finds all methods marked with a {@link Produce} annotation.
   *  寻找所有的 @Produce 方法，封装成 EventProducer
   *  */
  static Map<Class<?>, EventProducer> findAllProducers(Object listener) {
    // 拿到目标类
    final Class<?> listenerClass = listener.getClass();
    Map<Class<?>, EventProducer> handlersInMethod = new HashMap<Class<?>, EventProducer>();

    // 去缓存中拿到 @Produce 的缓存Map
    Map<Class<?>, Method> methods = PRODUCERS_CACHE.get(listenerClass);
    if (null == methods) {
      methods = new HashMap<Class<?>, Method>();
      loadAnnotatedProducerMethods(listenerClass, methods);
    }
    // 逐个将对应的类和Produce方法放入 EventProducer 和 handlersInMethod内
    if (!methods.isEmpty()) {
      for (Map.Entry<Class<?>, Method> e : methods.entrySet()) {
        EventProducer producer = new EventProducer(listener, e.getValue());
        handlersInMethod.put(e.getKey(), producer);
      }
    }

    return handlersInMethod;
  }

  /** This implementation finds all methods marked with a {@link Subscribe} annotation. */
  static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object listener) {
    Class<?> listenerClass = listener.getClass();
    Map<Class<?>, Set<EventHandler>> handlersInMethod = new HashMap<Class<?>, Set<EventHandler>>();

    Map<Class<?>, Set<Method>> methods = SUBSCRIBERS_CACHE.get(listenerClass);
    if (null == methods) {
      methods = new HashMap<Class<?>, Set<Method>>();
      loadAnnotatedSubscriberMethods(listenerClass, methods);
    }
    if (!methods.isEmpty()) {
      for (Map.Entry<Class<?>, Set<Method>> e : methods.entrySet()) {
        Set<EventHandler> handlers = new HashSet<EventHandler>();
        for (Method m : e.getValue()) {
          handlers.add(new EventHandler(listener, m));
        }
        handlersInMethod.put(e.getKey(), handlers);
      }
    }

    return handlersInMethod;
  }

  private AnnotatedHandlerFinder() {
    // No instances.
  }

}
