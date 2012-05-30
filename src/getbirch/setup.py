import os,shutil,urllib

from Globals import ARGS
from Globals import CONSOLE
from Globals import print_console
from util import wget
from util import untar
#from metastore import restoreMetaData
"""
Verify that the dependancies are present
"""
	
def check_depends():
	def check_depend(depend):
		#if the dependency is found, then this will evaluate to true, else, false
		found= os.popen("which "+depend).read().strip() !=""
		
		print_console("Dependancy \""+depend+"\" found: "+str(found))
		if (not found):
			return False
		else:
			return True
	
	dependancies=["csh","java","python"]
	foundall=True
	
	for each in dependancies:
		foundall=check_depend(each)	
		if (not foundall):
			print_console("Dependancy " + each+ " not found on system path!")
			return False
		
	return foundall


def clobber_check():

		
		if (os.path.lexists(ARGS.install_dir)):
				contents=os.listdir(ARGS.install_dir)
				
				if (contents.count("local")!=0 or os.path.exists(ARGS.install_dir+"/admin/BIRCH.properties")):
					print_console("ERROR! The directory specified already contains a BIRCH installation! Aborting")
					JOptionPane.showMessageDialog(None,"Installation cancelled to prevent clobbering existing installation.\nPlease restart installer and select a different directory, or remove old installation")
					shutdown()
		else:
			check=os.popen("echo $BIRCH").read().strip()
			if (check!=None and check!=""):

				print_console("An existing BIRCH installation was found at:\""+check+"\"")

			
				if (os.path.lexists(check) and os.path.samefile(check, ARGS.install_dir)):
					message="The installation directory that you specified:\n"+ARGS.install_dir+"\nAlready contains a BIRCH installation. Are you sure you wish to proceed?(NOT recommended)"
					reload = JOptionPane.showConfirmDialog(None,message, "Input",JOptionPane.YES_NO_OPTION);
				
					if (reload==JOptionPane.NO_OPTION):
						print_console("User aborted install.")
						shutdown()
					
					else:
						print_console("User decided to install over old installation.")
		return		

"""
Copies the jython interpretter to the install directory
"""
def copy_jython():
		shutil.copy(ARGS.jython_path, ARGS.install_dir+"/java/jython.jar")

"""
Fetches the framework of the type depending on which flags were set by the GUI
"""
def get_framework():
		
		url=None
		
		os.chdir(ARGS.install_dir)
		if(ARGS.isGitDev):
			ARGS.GITDEV_HASHMAP["h"]=ARGS.selectedTag[1]
			params=urllib.urlencode(ARGS.GITDEV_HASHMAP)
			ARGS.FRAMEWORK_URL=ARGS.GITDEV_URL+"%s"%params
			url=ARGS.FRAMEWORK_URL
			print_console("Using Git: Downloading development framework to " + ARGS.install_dir + " from " + url)
			print_console("Please wait... a fresh archive is being cooked up...")
		else:
	  		
			if (not ARGS.is_mini and not ARGS.is_development):
				
				url=ARGS.FRAMEWORK_URL
				url=url.replace("$TYPE",ARGS.NEWEST_VERSION)
				print_console( "Downloading birch framework to " + ARGS.install_dir + " from " + url)
				
			elif (not ARGS.is_development and ARGS.is_mini):
				
				url=ARGS.MINI_URL
				url=url.replace("$TYPE",ARGS.NEWEST_VERSION)
				print_console( "Downloading miniBirch framework to " + ARGS.install_dir + " from " + url)
			
			elif(ARGS.is_development and not ARGS.is_mini):
				
				url=ARGS.FRAMEWORK_URL
				url=url.replace("$TYPE","D")
				url=url.replace("CURRENT","Development")
				print_console( "Downloading development framework to " + ARGS.install_dir + " from " + url)
				
			elif(ARGS.is_development and ARGS.is_mini):
				url=ARGS.MINI_URL
				url=url.replace("$TYPE","D")
				url=url.replace("CURRENT","Development")
				print_console( "Downloading miniBirch development framework to " + ARGS.install_dir + " from " + url)
		
	  		
		wget(url, "framework.tar.gz",CONSOLE)

"""
Fetches all binaries selected by the GUI
"""
def get_binaries():
	
	os.chdir(ARGS.install_dir)
	
	def get_binary(platform):
		url = ARGS.BIN_URL
		url=url.replace("$PLATFORM",platform)
		
		if (ARGS.is_development):
			url=url.replace("$TYPE","D")
	  		url=url.replace("CURRENT","Development")
		else:
			url=url.replace("$TYPE",ARGS.NEWEST_VERSION)
			
		print_console( "Downloading binaries for platform \"" + platform  + "\" to " + ARGS.install_dir + " from " + url)
 		wget(url, platform+".tar.gz",CONSOLE)
 		
	if (not ARGS.multi_bins):
		get_binary(ARGS.platform)
 	else:
 		
 		for each in ARGS.platforms:
 			get_binary(each)

	
def extract_tarballs(windows=False):
	def extract_binary(binary):
		if (os.path.lexists(ARGS.install_dir +"/"+binary+ ".tar.gz")):
			if (windows):
				cygwin_untar(ARGS.install_dir +"/"+binary+ ".tar.gz",binary+ ".tar.gz")

			else:
				untar(binary+ ".tar.gz")	
		else:
			print_console( "The required file "+binary+ ".tar.gz was not found")
	
	
	
	print_console( "Decompressing framework...")
	os.chdir(ARGS.install_dir)
	
	if (os.path.lexists(ARGS.install_dir + "/framework.tar.gz")):

		if (windows and not ARGS.isGitDev):
			cygwin_untar(ARGS.install_dir + "/framework.tar.gz","framework.tar.gz")
		elif(not ARGS.isGitDev):
			untar("framework.tar.gz")
		elif (ARGS.isGitDev):
			if (windows):
				cygwin_untar(ARGS.install_dir + "/framework.tar.gz","framework.tar.gz",noroot=True)
			else:
				excludelist=list()
				if (ARGS.is_update):
					exclude.append("local")
				untar("framework.tar.gz",path=".",noroot=True,exclude=excludelist)
			#print_console("Restoring permission/time data.")
			#restoreMetaData(ARGS.install_dir)
			#print_console("Metadata restored.")
	else:
		print_console( "The required file framework.tar.gz was not found")
		return

	print_console( "Decompressing binaries...")
	
	if (not ARGS.multi_bins):
		extract_binary(ARGS.platform)
	else:
		
		for each in ARGS.platforms:
			extract_binary(each)
			
from cygcfg import cygwin_untar
from cygcfg import cygwin_exec
