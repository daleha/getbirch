'''
Created on Jun 14, 2010

@modified: Jun 14, 2010
@author: Dale Hamel
@contact: umhameld@cc.umanitoba.ca
'''
#python libs
import traceback
import os
import shutil
import sys
import time
import subprocess
import tarfile
from subprocess import *

from install import *
from cygcfg import *
from commonlib import *
from setup import *
import Globals

import nobirch
import birchhome

from Globals import CONSOLE
from Globals import ARGS
from Globals import fetch_git_url
from Update import update_birch
from util import quote_dos_path

import javax.swing.JOptionPane as JOptionPane



def disc_prepare():
	
	binaries= tarfile.open(ARGS.top_dir+"/binaries.tar.gz")
	
	def untar_binary_tar(name):
		print_console("Extracting "+name+ " to "+ARGS.install_dir+"/"+name)
		binaries.extract(name,ARGS.install_dir)
	
	print_console("This is a disc install, must decompress binaries to install directory")
	
	print_console("Copying framework to installation directory...")
	shutil.copy(ARGS.top_dir+"/framework.tar.gz", ARGS.install_dir)
	
	print_console("Copying binaries to installation directory...")
	
	
	for each in ARGS.platforms:
		
		untar_binary_tar(each+".tar.gz")
	
	


	

def install(fetch=True):
	print_label('Starting the intall process')	
	print_console("The current version of BIRCH is "+ARGS.NEWEST_VERSION)
	print_console("Using: "+ARGS.install_dir+" as installation directory")
	
	if "winxp-32" in ARGS.platform:

		quotedBasePath=quote_dos_path(ARGS.install_dir).replace("\"","\\\"")+"\\\""
		print_console("Retrieving unix compatibility layer")
		get_cygwin_installer()
		ARGS.jython_path=quote_dos_path(ARGS.jython_path.replace("\\","/"))
		
		install_cygwin()
		if (fetch):
			print_label('Fetching archives')
			get_framework()
			get_binaries()

		extract_tarballs(windows=True)

		print_console("Making birch local")
		cygwin_exec("mv /home/BIRCH/local-generic /home/BIRCH/local")
		print_console("Making birch properties")
		makeProperties("/home/BIRCH")
		print_console("Running birch home")
		#run_birchhome(ARGS.install_dir+"/","/home/BIRCH")

		command="cd  /home/BIRCH/install-birch/ && ./birchhome.sh"
		cygwin_exec(command)
		set_platform(exec_func=cygwin_exec,install_dir="/home/BIRCH")
		makeParamFile("/home/BIRCH")

		#these seem broken, everything else is fine
		run_customdoc(cygwin_exec,"/usr/bin/python","/home/BIRCH")
		run_htmldoc(cygwin_exec,"/usr/bin/python","/home/BIRCH")
		cygwin_exec("/home/BIRCH/admin/newuser")
		#need a way to get biolegato to work... python wrapper for java calling full executable?
		
	else:
		if (fetch):
			print_label('Fetching archives')
			get_framework()
			get_binaries()


		extract_tarballs()
	
		print_console("Archives extracted.")
		run_nobirch()
		move_local()	
		makeProperties(ARGS.install_dir)
		run_birchhome(ARGS.install_dir+"/",ARGS.install_dir+"/")
		set_platform()	#simply calling existing setplatform
		makeParamFile(ARGS.install_dir) #see if this can be omitted, its a total hack
		run_customdoc(stream_exec,"java -jar "+ARGS.jython_path,ARGS.install_dir)
		run_htmldoc(stream_exec,"java -jar "+ARGS.jython_path,ARGS.install_dir)	
		run_newuser()
	
	#There is a module to do this anyways (down)
	#shutil.copy(ARGS.jython_path, ARGS.install_dir+"/java/jython.jar")
		
	verify_install()
		
		



def run_main(cwd):
	print_label("Running getbirch installer process")

	if (ARGS.is_update):
		print_console("Updating an existing BIRCH installation")

	ARGS.set_installdir(cwd)
		
	

	if "winxp-32" in ARGS.platform:
		print_console("This is a windows install")
		has_depends=True
	else:
		has_depends=check_depends()
	
	if (not has_depends):
		print_console("A required dependancy is not present on system path, BIRCH cannot be installed")
		shutdown()
	
	if (not ARGS.is_update):
		
		clobber_check()
	
		if (os.path.lexists(ARGS.install_dir)):
			os.chdir(ARGS.install_dir)
		
		fetch = True
		if (ARGS.disc_install):
			disc_prepare()
			fetch = False
	
		install(fetch)

	else:
		update_birch()
		

	
	
	
def main(cwd):

	try:
 
		start = time.time()

		run_main(cwd) #this will produce a crash: must be called as module

		end = time.time()
		elapsed= end - start
		min = elapsed/60
		print_console("Installation took "+str(min)+" minutes.")

		JOptionPane.showMessageDialog(None,"Please bookmark BIRCH custom documentation, which will appear in a web browser")
		showCustomDoc()
		JOptionPane.showMessageDialog(None,"Installation completed successfully.")

	except:
		
	#	print_console(err)
		err=traceback.format_exc()
		print_console(err)
		#print_console("Sending bug to bugmail")
		#ARGS.log_file.flush()
		#ARGS.log_file.close()
		#from bugmail import send_bug
		#send_bug()
		JOptionPane.showMessageDialog(None,"Installation failed, please try again and submit install log as a bug report.")


	finally:
		print_console("Installation finished")
		shutdown()



if __name__ == "__main__":
	sys.exit(main())
