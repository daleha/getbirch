package JarHandler;



import java.io.File;

import javax.swing.JOptionPane;

import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class JarUtil {
	public static String getPath(){
		String basePath;
		File tempFile;
		File parent;

		parent=new File(JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());

		basePath=parent.getAbsolutePath();

		basePath=basePath.substring(0,basePath.indexOf("file:"));

		tempFile = new File(basePath+File.separator+"getbirch.jar");

		if (!tempFile.exists()){
			//unix systems will likely use this basepath
			basePath=parent.getAbsolutePath();
			basePath=basePath.substring(basePath.indexOf("file:")+5, basePath.indexOf("!"));

		}

		if (basePath.indexOf("getbirch.jar")>=0){
			basePath=basePath.replace("getbirch.jar", "");
		}
		
		

		return basePath;
	}
	
	//this method needs to be cleaned up so that it works on solaris and windows
	public static boolean cleanUp(String toDelete){
		boolean success;
		File file;
		PythonInterpreter interp = new PythonInterpreter();
		
		interp.exec("import shutil");
		interp.exec("import os");
		interp.set("temp_dir", new PyString(toDelete));
		interp.exec("shutil.rmtree(temp_dir, ignore_errors=True)");
		interp.exec("os.system(\"rm -rf temp_dir\")");
		
		file = new File(toDelete);
		success=file.delete();
		if (file.isDirectory()){
			success=deleteDir(file);
		}
		
		return success;
	}
	
	private static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
}
