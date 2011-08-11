package JarHandler;


import java.io.*;
import javax.swing.*;


public class Fetcher{

	public String getResource(String path,String newFilePath){
		String thisJarPath;
		String basePath;
		byte[] contents;
		JarResources jar;
		String outPath;
		File tempDir;
		File tempFile;

		File parent=new File(Fetcher.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		basePath=parent.getAbsolutePath();
		
		basePath=basePath.substring(0,basePath.indexOf("file:"));
		
		tempFile = new File(basePath+File.separator+"getbirch.jar");
		
		if (!tempFile.exists()){
			//unix systems will likely use this basepath
			basePath=parent.getAbsolutePath();
			basePath=basePath.substring(basePath.indexOf("file:")+5, basePath.indexOf("!"));
			basePath=basePath.replace("getbirch.jar","");
		}
		

		
		thisJarPath=basePath+"getbirch.jar";

		
		//JOptionPane.showMessageDialog(null,thisJarPath);
		jar = null;
		
		try{
			jar = new JarResources(thisJarPath);
		}catch(Exception E){
			JOptionPane.showMessageDialog(null,"Failed to decompress jarfile");
			System.out.println(E.getMessage());
		}
		
		contents= jar.getResource(path);
		if (contents==null){
			JOptionPane.showMessageDialog(null,"contents not read");		
		}else{
			//JOptionPane.showMessageDialog(null,"contents read");
		}

		outPath=null;
		try{
			File outfile = new File(newFilePath);
			FileOutputStream fos = new FileOutputStream(outfile);
			fos.write(contents);
			fos.flush();
			fos.close();
			outPath=outfile.getAbsolutePath();
			//JOptionPane.showMessageDialog(null,outPath);
		}catch(IOException IOE){
			System.out.println(IOE.getMessage());		
		}

		return outPath;
	}
}
