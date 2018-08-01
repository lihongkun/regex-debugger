package com.lihongkun.regex;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

public class RegexDebuggerTransformer implements ClassFileTransformer{
	
	private static final String HACK_CLASS_NAME_PREFIX = Pattern.class.getName()+"$";
	private static final String HACK_CLASS_NAME_EXCLUDE = "CharProperty";
	private static final String HACK_METHOD_NAME = "match";
	
	public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		
		className = className.replace("/", ".");
		CtClass ctClass = null;
		try {
			//使用全称,用于取得字节码类
			ctClass = ClassPool.getDefault().get(className);
			
			// Pattern 的子类才进行方法转换
			if(className.startsWith(HACK_CLASS_NAME_PREFIX) && className.indexOf(HACK_CLASS_NAME_EXCLUDE) < 0){
				//所有方法
				CtMethod[] ctMethods = ctClass.getDeclaredMethods();
				
				for(CtMethod ctMethod:ctMethods){
					// match 方法才进行
					if(ctMethod.getName().equals(HACK_METHOD_NAME))
						transformMethod(ctMethod, ctClass);
				}
			}
			
			return ctClass.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void transformMethod(CtMethod ctMethod,CtClass ctClass) throws Exception{
		
		if((ctMethod.getModifiers()&Modifier.ABSTRACT)>0){
			return;
		}
		
		String methodName = ctMethod.getName();
		String newMethodName = methodName + "$hack";
		
		ctMethod.setName(newMethodName);
		CtMethod newMethod = CtNewMethod.copy(ctMethod,methodName, ctClass, null);//创建新的方法，复制原来的方法 ，名字为原来的名字
		
		newMethod.insertBefore("System.out.print(\""+ctClass.getSimpleName()+"."+ctMethod.getName()+" ==> \");System.out.println($2);");
		
		ctClass.addMethod(newMethod);//增加新方法   
	}

}
