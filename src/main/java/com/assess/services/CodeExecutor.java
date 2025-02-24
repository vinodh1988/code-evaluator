package com.assess.services;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.stereotype.Service;

import com.assess.entities.Result;

@Service
public class CodeExecutor {
	 /*public String compileAndExecute(String code, String className, String methodName, String personName) throws Exception {
	        // Create a folder named after the person and the current date
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String folderName = personName + "_" + sdf.format(new Date());
	        String folderPath = "E:\\code\\" + folderName;
	        File folder = new File(folderPath);
	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        // Write the code to a temporary file inside the new folder
	        String fileName = folderPath + "\\" + className + ".java";
	        try (FileWriter writer = new FileWriter(fileName)) {
	            writer.write(code);
	        }

	        // Compile the code
	        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	        int result = compiler.run(null, null, null, fileName);

	        if (result == 0) {
	            // Load the compiled class
	            File file = new File(folderPath);
	            ClassLoader classLoader = new java.net.URLClassLoader(new java.net.URL[]{file.toURI().toURL()});
	            Class<?> dynamicClass = classLoader.loadClass(className);

	            // Invoke the method
	            Method method = dynamicClass.getDeclaredMethod(methodName);
	            Object returnValue = method.invoke(null);
	            System.out.println("Return value: " + returnValue);
	            
	            return "Returning: "+returnValue;
	            
	        } else {
	            System.out.println("Compilation Failed!");
	            return "Compilation Failed";
	        }

	        // Clean up the temporary file (optional)
	      
	    }*/

    public Result compileAndExecute(String code, String personName,String sourceClass) {
    	
            String className = "TestClass";
            String methodName = "test";

            try {
                // Create a folder named after the person and the current date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String folderName = personName + "_" + sdf.format(new Date());
                String folderPath = "/home/azureuser/codingtest/" + folderName;
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    if (!folder.mkdirs()) {
                        return new Result("error", "Failed to create directory: " + folderPath);
                    }
                }

                // Write the code to a temporary file inside the new folder
                String fileName = folderPath + "/" + className + ".java";
                try (FileWriter writer = new FileWriter(fileName)) {
                    writer.write(code);
                }

                // Compile the code
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                if (compiler == null) {
                    return new Result("error", "Java Compiler not available");
                }

                int result = compiler.run(null, null, null, fileName);
                if (result != 0) {
                    return new Result("compile error", "Compilation failed");
                }

                // Load the compiled class
                File file = new File(folderPath);
                URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
                Class<?> dynamicClass = Class.forName(className, true, classLoader);
                if (dynamicClass == null ) {
                    return new Result("error", "Class not found: " + className);
                }
               
                Method[] methods = dynamicClass.getDeclaredMethods();

                // Iterate through the methods and print the static ones
                for (Method method : methods) {
                
                    if (Modifier.isStatic(method.getModifiers())) {
                        System.out.println("Static method: " + method.getName());
                    }
                }
             //   Class<?> codeClass = Class.forName(sourceClass,true,classLoader);
              
          /*      if(codeClass==null) {
                	 return new Result("error", "Class not found: " + codeClass);
                }*/

                // Invoke the test method
                Method method = dynamicClass.getDeclaredMethod(methodName);
                if (method == null) {
                    return new Result("error", "Method not found: " + methodName);
                }

                Object returnValue = method.invoke(null);
                if (returnValue == null) {
                    return new Result("error", "Method invocation returned null");
                }

                return new Result("success", returnValue.toString());

            } catch (Exception e) {
            	e.printStackTrace();
                return new Result("exception", e.getMessage());
            }
    }
}
