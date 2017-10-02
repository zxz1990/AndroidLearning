package com.example.zxz.androidtest.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SuppressWarnings("rawtypes")
public class ReflectUtils {
	protected final static boolean DEBUG = false;
	public static int count = 0;
	public static long totalCost = 0;
	public static long totalCost1 = 0;
	public static long totalCost2 = 0;
	public static long totalCost3 = 0;
	public static long totalCost4 = 0;
	public static long totalCost5 = 0;
	public static long totalCost6 = 0;
	public static long time = 0;

	// 从com.android.internal.R等资源文件中读取常量
	public static boolean getInternalBoolean(String inerClass, String fieldName) {
		boolean bool = false;
		try {
			Class c = Class.forName(inerClass);
			Object obj = c.newInstance();
			Field field = c.getField(fieldName);
			makeAccessible(field);
			bool = field.getBoolean(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (DEBUG) {
			Log.i("ReflectUtils", "getInternalBoolean bool = " + bool);
		}
		return bool;
	}

	public static void  clear() {
		totalCost = 0;
		totalCost1 = 0;
		totalCost2 = 0;
		totalCost3 = 0;
		totalCost4 = 0;
		totalCost5 = 0;
		totalCost6 = 0;
		count = 0;
	}

	public static void  print() {
		totalCost = totalCost1 + totalCost2 + totalCost3 + totalCost4 + totalCost5 + totalCost6;
		Log.d("TimeDebug","total "+totalCost + " time1 "+totalCost1 + " time2 "+ totalCost2 + " time3 "+totalCost3 + " time4 "+ totalCost4 + " time5 "+totalCost5 +" time6 "+totalCost6);
		clear();
	}

	public static int getInternalInt(String inerClass, String fieldName) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
		int id = 0;
		try {
			Class c = Class.forName(inerClass);
			Object obj = c.newInstance();
			Field field = c.getField(fieldName);
			makeAccessible(field);
			id = field.getInt(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (DEBUG) {
			totalCost1 += System.currentTimeMillis() - time;
		}
		return id;
	}

	public static int[] getInternalIntArray(String inerClass, String fieldName) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
		int[] id = new int[0];
		try {
			Class c = Class.forName(inerClass);
			Object obj = c.newInstance();
			Field field = c.getField(fieldName);
			makeAccessible(field);
			id = (int[]) field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (DEBUG) {
			totalCost2 += System.currentTimeMillis() - time;
		}
		return id;
	}

	// 从com.android.internal.R中读取常量 end

	/**
	 * 执行某对象的方法
	 *
	 * @param object
	 *            所属对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            参数类型数组
	 * @param parameters
	 *            参数值
	 * @return 方法的返回值
	 * @throws Exception
	 */
	public static Object invokeMethod(final Object object, final String methodName, Class<?>[] parameterTypes, final Object[] parameters) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			Object obj =  method.invoke(object, parameters);
			if(DEBUG) {
				totalCost3 += System.currentTimeMillis() - time;
			}
			return obj;
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	public static Object invokeMethod(final Object object, final String methodName, final Object[] parameters) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
		Class[] parameterTypes = new Class[parameters.length];
		for (int i = 0, j = parameters.length; i < j; i++) {
			parameterTypes[i] = parameters[i].getClass();
		}

		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			Object obj =   method.invoke(object, parameters);
			if(DEBUG) {
				totalCost4 += System.currentTimeMillis() - time;
			}
			return obj;
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod.
	 *
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes, boolean countTime) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
		Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);
		if(DEBUG) {
			totalCost6 += System.currentTimeMillis() - time;
		}
		return method;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod.
	 *
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		return getDeclaredMethod(object.getClass(), methodName, parameterTypes);
	}

	private static Method getDeclaredMethod(Class<?> object, String methodName, Class<?>[] parameterTypes) {
		for (Class<?> superClass = object; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {// NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 不建议使用，目前不能调用父类的方法
	 *
	 * @param owner
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public static Object invokeSuperMethod(Object owner, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Exception {
		Method method = getDeclaredMethod(owner.getClass().getSuperclass(), methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + owner + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(owner, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 得到某个对象的隐藏属性
	 *
	 *            所属对象
	 * @param fieldName
	 *            属性名
	 * @return 隐藏属性的值
	 * @throws Exception
	 */
	public static Object getProperty(Object object, String fieldName) throws Exception {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 强行设置Field可访问.
	 */
	private static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 *
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	private static Field getDeclaredField(final Object object, final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	private static Field getDeclaredField(Class<?> object, final String fieldName) {
		for (Class<?> superClass = object; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	public static void setProperty(Object object, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到某个类的静态属性
	 *
	 * @param className
	 *            类名
	 * @param fieldName
	 *            属性名
	 * @return 隐藏属性的值
	 * @throws Exception
	 */
	public static Object getStaticProperty(String className, String fieldName) {
		try {
			Class<?> ownerClass = Class.forName(className);
			Field field = getDeclaredField(ownerClass, fieldName);
			if (field == null) {
				throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + className + "]");
			}

			makeAccessible(field);

			Object result = null;
			try {
				result = field.get(ownerClass);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		return invokeStaticMethod(className, methodName, argsClass, args);
	}

	public static Object invokeStaticMethod(String className, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			Class<?> ownerClass = Class.forName(className);
			Method method = getDeclaredMethod(ownerClass, methodName, parameterTypes);
			if (method == null) {
				throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + className + "]");
			}

			method.setAccessible(true);
			return method.invoke(null, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 带参数的构造方法使用该方法实例化对象
	 *
	 * @param className
	 *            类名
	 * @param args
	 *            构造方法需要的参数
	 * @return 类的实例对象
	 * @throws Exception
	 */
	public static Object newInstance(String className, Object[] args) throws Exception {
		Class<?> newoneClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Constructor<?> cons = newoneClass.getConstructor(argsClass);
		cons.setAccessible(true);
		return cons.newInstance(args);
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		return convertReflectionExceptionToUnchecked(null, e);
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(String desc, Exception e) {
		desc = (desc == null) ? "Unexpected Checked Exception." : desc;
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(desc, e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(desc, ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(desc, e);
	}

	public static boolean isLayoutRtl(ViewGroup view) {
//		try {
//			return (Boolean) ReflectUtils.invokeMethod(view, "isLayoutRtl", new Object[0]);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return false;
	}

    /**
     * View的FocusFlag
     */
    private static final int PFLAG_FOCUSED = 0x00000002;

    /**
     * 设置View FocusFlag
     * @param view
     * @param focused
     */
    public static void setViewFocusFlag(View view, boolean focused) {
		if(DEBUG) {
			count++;
			time = System.currentTimeMillis();
		}
        if (view == null) {
            return;
        }
        if (view.hasFocus() && !focused) {
            int flags = getViewFlags(view);
            if (flags != -1) {
                flags &= ~PFLAG_FOCUSED;
                setFlags(view, flags);
            }
        } else if (!view.hasFocus() && focused) {
            int flags = getViewFlags(view);
            if (flags != -1) {
                flags |= PFLAG_FOCUSED;
                setFlags(view, flags);
            }
        }
		if(DEBUG) {
			totalCost5 += System.currentTimeMillis() - time;
			Log.d("TimeDebug","setFlag "+(System.currentTimeMillis() - time));
		}
    }

    /**
     * 设置View的私有变量
     *
     * @param view
     * @param flags
     */
    public static void setFlags(View view, int flags) {
        if (flags == -1 || view == null) {
            return;
        }
        try {
            ReflectUtils.setProperty(view, "mPrivateFlags", flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 设置View的私有变量
	 *
	 * @param view
	 */
	public static void setScroll(View view, int scrollX, int scrollY) {
		try {
			ReflectUtils.setProperty(view, "mScrollX", scrollX);
			ReflectUtils.setProperty(view, "mScrollY", scrollY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 获取View私有的成员变量
     *
     * @param view
     * @return
     */
    public static int getViewFlags(View view) {
        try {
			count++;
            Object flags = ReflectUtils.getProperty(view, "mPrivateFlags");
            return flags == null ? -1 : (Integer) flags;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

	public static void copyFields(Object source, Object target)  {
		if(source == null || target == null){
			return;
		}
		Class sourceClass = source.getClass();//得到对象的Class
		Class targetClass = target.getClass();//得到对象的Class

		Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
		Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性

		try {
			int i = 0;
			for (Field fd : sourceFields) {
				targetFields[i].set(target,sourceFields[i].get(source));
				i++;
			}
		}catch (Exception e){
		}
	}
}
