
import java.lang.Runtime;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

import javax.swing.*;

import wizard.Wizard;
import wizard.WizardPanelDescriptor;
import content.AdvancedPanel;
import content.AdvancedPanelDescriptor;
import content.InstallTypePanel;
import content.InstallTypePanelDescriptor;

import content.StartPanel;
import content.StartPanelDescriptor;

import JarHandler.JarUtil;
import JarHandler.Fetcher;

import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyTuple;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;

public class Main {
	private static String platform;
	private static String version;
	private static int MAX_RECURSION_DEPTH = 10000000;
	private static PythonInterpreter interp;
	private static boolean isDiscInstall;
	private static boolean createBackup;

	public static void doMain() throws PyException {

		String installDir;//this is a comment
		String logDir;
		String frameType;
		String adminEmail;
		String[] selectedTag;
		boolean install;
		ArrayList<String> binaries;

		interp = new PythonInterpreter();
//		isDiscInstall = is_disc_install();

//		memCheck();


//		platform = detect_platform();
//		version = detect_version();

		Wizard wizard = new Wizard();
		wizard.getDialog().setTitle("Getbirch BIRCH Install Wizard");

		WizardPanelDescriptor descriptor = new StartPanelDescriptor(wizard);
		wizard.registerWizardPanel(StartPanelDescriptor.IDENTIFIER, descriptor);

		WizardPanelDescriptor descriptor1 = new InstallTypePanelDescriptor(
				wizard);
		wizard.registerWizardPanel(InstallTypePanelDescriptor.IDENTIFIER,
				descriptor1);

		WizardPanelDescriptor descriptor2 = new AdvancedPanelDescriptor();
		wizard.registerWizardPanel(AdvancedPanelDescriptor.IDENTIFIER,
				descriptor2);
		//((AdvancedPanelDescriptor) descriptor2).setPlatform(platform);
		//((AdvancedPanelDescriptor) descriptor2).setVersion(version);

		wizard.setCurrentPanel(StartPanelDescriptor.IDENTIFIER);
/*		if (isDiscInstall) {
			((InstallTypePanel) descriptor1.getPanelComponent())
					.setDiscInstall();
			((AdvancedPanelDescriptor) descriptor2).setDiscInstall();
		}
        */

		int ret = wizard.showModalDialog();

/*
		System.out
				.println("Dialog return code is (0=Finish,1=Cancel,2=Error): "
						+ ret);

		if (ret == 1) {
			System.exit(0);
		}

		if (((InstallTypePanel) descriptor1.getPanelComponent())
				.isDefaultSelected()) {
			System.out.println("Proceeding with default install");
		} else {
			System.out.println("An advanced install was selected");
		}

		install = ((StartPanel) descriptor.getPanelComponent())
				.isDefaultSelected();
		if (install) {
			installDir = ((AdvancedPanel) descriptor2.getPanelComponent())
					.getInstallDir();
		} else {
			installDir = ((StartPanel) descriptor.getPanelComponent())
					.getUpdateDir();
		}

		createBackup = ((StartPanel) descriptor.getPanelComponent())
				.makeBackup();
		frameType = ((AdvancedPanel) descriptor2.getPanelComponent())
				.getFrameworkVersion();
		binaries = ((AdvancedPanel) descriptor2.getPanelComponent())
				.getBinariesSelected();
		logDir = ((AdvancedPanel) descriptor2.getPanelComponent()).getLogDir();
		boolean devel = ((AdvancedPanel) descriptor2.getPanelComponent())
				.isDevelopment();
		
		
		if (install)
			adminEmail=((StartPanel) descriptor.getPanelComponent()).getEmail();
		else
			adminEmail="";
		
		
		startPhase1(install, installDir, logDir, frameType, binaries, devel,adminEmail);

		System.exit(0);
*/
	}
/*
	private static boolean is_disc_install() {
		final String BIN = "binaries.tar.gz";
		final String FRAME = "framework.tar.gz";
		boolean isDisc;
		String path;
		File currDir;
		String[] raw_contents;

		ArrayList<String> contents;

		isDisc = false;

		path = JarUtil.getPath();
		currDir = new File(path);

		if (currDir.isDirectory()) {
			raw_contents = currDir.list();
			contents = new ArrayList<String>(Arrays.asList(raw_contents));
			// JOptionPane.showMessageDialog(null,contents.toString());

			if (contents.contains(LIB) && contents.contains(BIN)
					&& contents.contains(FRAME)) {
				// JOptionPane.showMessageDialog(null,"This has been detected as a disc installation");
				isDisc = true;
			}

		} else {
			JOptionPane
					.showMessageDialog(null, "Not current directory:" + path);
		}

		return isDisc;

	}
    */

/*
	private static String detect_platform() {
		String platform;
		String path;

		// the probes will go to the homedir
		path = System.getProperty("user.home") + "/getbirch_temp";

		new File(path).mkdir();

		interp.exec("import Globals");
		interp.set("temp_dir", new PyString(path));

		interp.exec("txt = Globals.detect_platform(temp_dir)");
		PyObject result = interp.get("txt");

		platform = result.toString();

		return platform;

	}
    */
/*
	private static String detect_version() {
		String version;

		interp.exec("import Globals");
		interp.exec("version = Globals.get_version()");
		PyObject result = interp.get("version");

		version = result.toString();

		return version;

	}
*/
	private static void startPhase1(
			boolean install, String installDir,
			String logDir, String frameType, ArrayList<String> binaries,
			boolean devel, String adminEmail
			) 
	{
		PythonInterpreter interp = new PythonInterpreter();


    /*
		// set local parameters

		interp.exec("import getbirch");
		interp.exec("import Globals");
		interp.exec("import sys");

		interp.set("MAX_RECURSION_DEPTH", new PyInteger(MAX_RECURSION_DEPTH));
		interp.exec("sys.setrecursionlimit(MAX_RECURSION_DEPTH)");
		interp.set("installDir", new PyString(installDir));

		interp.set("jythonpath", new PyString(jythonPath));
		interp.exec("Globals.set_jython_path(jythonpath)");

		if (isDiscInstall) {

			interp.set("top_dir", new PyString(JarUtil.getPath()));
			interp.exec("Globals.set_disc_install(top_dir)");
		}

		if (devel) {
			interp.exec("Globals.set_development()");

		}


		if (!install) {
			interp.exec("Globals.set_update()");
		}

		if (logDir != null) {
			interp.set("logDir", new PyString(logDir));
			interp.exec("Globals.set_log_dir(logDir)");
		}

		if (frameType != "") {
			interp.set("frameType", new PyString(frameType));
			interp.exec("Globals.set_frame_type(frameType)");
		}
		
		if (adminEmail != null) {
			interp.set("adminEmail", new PyString(adminEmail));
			interp.exec("Globals.set_admin_email(adminEmail)");
		}

		if (binaries.size() != 0) {
			interp.set("binaries", new PyList(binaries));
			interp.exec("Globals.set_binaries(binaries)");
		}

		if (createBackup) {
			interp.exec("Globals.set_make_backup()");
		}

		interp.exec("getbirch.main(installDir)");

    */
}


	public static void runCommand(String command) {
		Runtime rt;
		Process p;
		String s;
		rt = java.lang.Runtime.getRuntime();

		try {
			p = rt.exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			System.out
					.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
			p.waitFor();
		} catch (Exception E) {
			System.out.println(E.getMessage());
		}

	}

}
