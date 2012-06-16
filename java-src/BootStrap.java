import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import JarHandler.JarUtil;
import java.awt.SplashScreen;

public class BootStrap {

    final String MINMEM="500";

    class StreamProxy extends Thread {
        final InputStream is;
        final PrintStream os;

        StreamProxy(InputStream is, PrintStream os) {
                this.is = is;
                this.os = os;
        }

        public void run() {
                try {
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line = null;
                        while ((line = br.readLine()) != null) {
                                os.println(line);
                        }
                } catch (IOException ex) {
                        throw new RuntimeException(ex.getMessage(), ex);
                }
        }
    }

    private String getBootString(){

        final String reloadCmd = "java -Xmx"+MINMEM+"m -Xmx"+MINMEM+"m -jar ";
        String path = JarUtil.getPath() + "getbirch.jar";
        String args = " -nobs";

        // fix windows paths
        if (path.indexOf("\\") >= 0) {
            path = "\"" + path + "\"";
        }

        return reloadCmd + path + args;


    }

    private void go(){
    	Process process= null;
        try {
                /*
                 * Spin up a separate java process calling a non-default Main class in your Jar.  
                 */

		 	
                process = Runtime.getRuntime().exec(getBootString());

                /*
                 * Proxy the System.out and System.err from the spawned process back to the user's window.  This
                 * is important or the spawned process could block.
                 */
                StreamProxy errorStreamProxy = new StreamProxy(process.getErrorStream(), System.err);
                StreamProxy outStreamProxy = new StreamProxy(process.getInputStream(), System.out);

                errorStreamProxy.start();
                outStreamProxy.start();

                System.out.println("Exit:" + process.waitFor());
        } catch (Exception ex) {
                System.out.println("There was a problem execting the program.  Details:");
                ex.printStackTrace(System.err);

                if(null != process){
                        try{
                                process.destroy();
                        } catch (Exception e){
                                System.err.println("Error destroying process: "+e.getMessage());
                        }
                }
        }
    }

    public static void main(String[] args) {
	boolean debootstrap=false;	
		
	for (int i = 0; i<args.length; i++){
		if (args[i].equals("-nobs")){
			debootstrap=true;
		}

	}
	if (!debootstrap){
		SplashScreen.getSplashScreen().close();				
		System.out.println("Calling the bootstrapper");

		new BootStrap().go();
	}else{
		System.out.println("Debootstrapping");
		Main.doMain();		
	}
    }

}
