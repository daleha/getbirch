About:
	This is the source tree for the application "getbirch". Getbirch is made possible by the project "onejar", that allows for
	a single jar to contain everything needed.

	What is getbirch? Getbirch, specifically, is a platform-independent installer for the bio-informatics application BIRCH,
	developed by Brian Fristensky at the University of Manitoba. 

	The major programming challenges involved in creating getbirch stem from it's aim to work the same on every platform that
	BIRCH runs on, including Solaris, Linux, OS-X, and most recently Windows. 

	At its core, getbirch acts like a turing machine that runs in java, and is basically a bootstrapper that loads up a
	jython virtual machine to run python scripts while only requiring that the user has java natively installed. On most 
	systems with mime typing configured correctly, this means that the user simply has to double-click on the jarfile (thanks to
	onejar), and it will extract itself, load up the jython virtual machine, and then begin to execute the python code to install
	BIRCH. 

	Why bother with jython? Why not simply run it in java? Well, put simply, the python code to install BIRCH is much simpler,
	and justifies the added complexity of loading up the jython virtual machine inside of the JVM. This also provides a really
	nice, clean framework to run any python script in while only requiring java as a dependency. 


Dependencies and building:

	To build getbirch, the following are required:
		sun java jdk (recommended v6 or later), with "javac", and "java" on the system path, and JAVA_HOME set.
		apache ant

	Simple executing "ant" from the getbirch source tree root will cause the project to be built. 

	The java code in java-src is called first, and from there calls to jython are made. 
	Python source files located in the src folder will be placed on the python classpath, and can be called
	from java by using the java jython interface. 

Details:

	Getbirch loads up a quick installer console, that will determine what platform the user is running it on by
	executing a suite of binary probes. If executed on a supported platform, one of these binary probes will give a
	result indicating what platform the user is running on. This value is passed along to the Arguments.py class,
	which is a configuration class.

	After the platform has been detected, a swing GUI is called that provides the user with basic options, such as
	where to install, where to log, etc. These values are also passed to Arguments.py. When the user clicks "finish",
	control is handed over from the java source to the python source, which proceeds to install BIRCH using the args
	specified.

	This process is mostly non-interactive, unless if an error occurs.
	
	The python source acts as an "input tape" running through the simulated PVM in jython. This is very heavily abstracted.

	This python source is running in a PVM (jython) inside a JVM, on the users machine. This adds a second level of 
	simulation. One of the major benefits gleamed from this is that the java source can call python modules, and 
	the python source can call java libraries. Hence, getbirch is really not written entirely in java or python, but
	in a hybrid language "jython".

Platform specific details:

	There are three categories:

	Classic unix (solaris and linux):
		This is how birch was designed to be installed. Basically, the BIRCH framework is fetched as a tarball,
		extracted, and the install scripts are run.
	BSD unix (OS-X);
		More or less the same as the classic unix install, but a wrapper "app" is created to allow BIRCH to be
		executed as a .app
	Cygwin (windows):
		The biggest hack of all three. In order to install BIRCH on windows, cygwin is installed, and a number
		of wrappers are placed inside of it to allow it to make calls to the native java. This is still in alpha.
