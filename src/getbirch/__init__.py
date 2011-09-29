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


#var imports
from Globals import CONSOLE
from Globals import ARGS

#core imports
from install import main_install
from Update import update_birch


#UI imports
#lib imports
from Globals import print_console

#jython imports
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
	
		main_install(fetch)

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
