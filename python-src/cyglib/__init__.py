import os
from re import *
from util import *
from shutil import *

from log import *
from Globals import *

CYGWIN_URL=BASE_URL+"Gitdev/cyghead.tar"



def setupCygwin(installDir):

    installDir = convert_pathsep(installDir)

    valid = True    
    valid = valid and getCygwinInstaller(installDir) 
    valid = valid and installCygwin(installDir) 


    return valid
    	

def getCygwinInstaller(installDir):
	cwd=os.getcwd()
	os.chdir(installDir)

    installerName="cyghead.tar"
	wget(CYGWIN_URL,installerName,CONSOLE)		
	untar(installerName)

	if (os.path.lexists(os.path.join(installDir,"/cyghead"))):	
		move( os.path.join(installDir,"cyghead/cygpak"), os.path.join(installDir,"cygpak"))
		move( os.path.join(installDir,"cyghead/setup.exe"), os.path.join(installDir,"setup.exe"))

	os.chdir(cwd)
    valid = os.path.isdir( os.path.join(installdir,"cygpak") ) and os.path.isfile( os.path.join(installDir, "setup.exe" ) ) 
    return valid



def installCygwin(installDir):
	cwd=os.getcwd()
	os.chdir(installDir)

    defaultOpts="-q -L " #for offline install
    eggfile="setuptools-0.6c11-py2.6.egg"

	COMMAND=quote_dos_path(installDir)+"/setup.exe"+" "+defaultOpts+" -l " + quote_dos_path( os.path.join (installDir, "cygpak")) + " -R " + quote_dos_path( os.path.join(installDir,"cygwin")+" -P wget,tcsh,python "
	debug("Using command: "+COMMAND)

	while (not os.path.lexists( os.path.join(installDir,"cygwin"))):#this is for windows 7, keep nagging for auth
		output=stream_exec(COMMAND,verbose=False)



	info("Linking BIRCH into home directory")
	cygwin_exec("ln -s "+quote_dos_path(installDir)+" /home/BIRCH", installDir)
	cygwin_exec("ln -s /home/BIRCH ",installDir)

	if (os.path.lexists( os.path.join(installDir,"/cyghead")) ):	
		info("Setting up cygwin to work with windows java")
		info("Installing easy_install and cygwinreg")
		cygwin_exec("cd /home/BIRCH/cyghead && sh "+eggfile, installDir)
		cygwin_exec("easy_install cygwinreg", installDir)

		cygwin_exec("mv /home/BIRCH/cyghead/java_bridge /bin/java", installDir)
		cygwin_exec("mv /home/BIRCH/cyghead/browser_bridge /bin/browser", installDir)
		cygwin_exec("mv /home/BIRCH/cyghead/apt-get /bin/apt-get",installDir)

		cygwin_exec("chmod +x /bin/java", installDir)
		cygwin_exec("chmod +x /bin/browser", installDir)
		cygwin_exec("chmod +x /bin/apt-cyg", installDir)
		info("Finished installing cygwin java compatability layer")
	else:
		error("Error! cyghead not found")
		raise Exception


	os.chdir(cwd)
    valid = True

    #add some sanity checks in here

    return valid

def cygwin_exec(command,installDir,verbosity=True,callbackfunc=None):

	command=command.replace("\"","\\\"")
	cmd= os.path.join(quote_dos_path( os.path.join(installDir,"cygwin")),"/bin/bash.exe")+ "-lc \""+command+"\""

	output=stream_exec(cmd,verbose=verbosity,callback=callbackfunc)
	return output


def convert_pathsep(path):
	path=path.replace("\\","/")
	return path
 

def quote_dos_path(path):

	def quotePath(match):
		return "/\""+match.group(1)+"\""

	path=path.replace("\\","/")
	cleanpath= sub ("/(.*?\s.*?[^/]*)",quotePath ,path)

	return cleanpath



