
#jython imports
import OutputConsole
import JarHandler.Fetcher as Fetcher

#var imports
from Arguments import Arguments

#system imports
import os
import urllib
import urllib2

#business logic imports -- REFACTOR
from platformdetect import PlatformDetector



PROGRAM = "getbirch.py: "
USAGE= "This program is called by a GUI classes, it is not command line interactive"
CONSOLE= OutputConsole()
ARGS=Arguments()

def get_version():
	try:
	
		raw_version=urllib2.urlopen(ARGS.VERSION_URL)
		
		ARGS.NEWEST_VERSION=raw_version.read().strip()
	
	except urllib2.HTTPError:
		print_console(PROGRAM+"Detecting version failed. Message:\n"+urllib2.HTTPError.message)
		exit()
	except:
		print_console(PROGRAM+"Could not detect the current version of BIRCH, exiting...")
		exit()

	return ARGS.NEWEST_VERSION

	
def detect_platform(temp_dir):
	fetcher = Fetcher()
	os.chdir(temp_dir)
	path= fetcher.getResource("resources/probes.tar.gz", temp_dir+"/probes.tar.gz")
	print_console(PROGRAM+"Extracted probes to:"+path)
	untar(path,temp_dir)

	detector = PlatformDetector(temp_dir+"/probes/")
	platform=detector.detect_platform()
	print_console(PROGRAM+"Detected platform as: "+platform)
	ARGS.platform=platform
	
	return platform	
"""
These methods are called from the java side to set initialize settings
"""

def set_admin_email(email):
	ARGS.admin_email=email

def set_disc_install(top_dir):
	ARGS.set_disc_install()
	ARGS.top_dir=top_dir
	

def set_log_dir(logdir):
	ARGS.set_log_dir(logdir)
	
def set_frame_type(frametype):
	
	if (frametype.find("D")>=0):
		ARGS.set_development()
		
		
	if (frametype.find("M")>=0):
		ARGS.set_mini()

def set_development():
	ARGS.set_development()

def set_binaries(binaries):
	
	
	for each in binaries:
		ARGS.add_platform(each)

def set_jython_path(jythonpath):
	ARGS.jython_path=jythonpath

def set_update():
	ARGS.is_update=True

def set_make_backup():
	ARGS.make_backup=True

def set_selected_tag(selected_tag):
	ARGS.isGitDev=True
	ARGS.selectedTag=selected_tag


from util import print_console
from util import untar

