
#jython imports
import OutputConsole




PROGRAM = "getbirch.py: "
USAGE= "This program is called by a GUI classes, it is not command line interactive"
CONSOLE= OutputConsole()

HEAD_PROJECT="birchdev.git"
BASE_URL="http://www.umanitoba.ca/faculties/afs/plant_science/psgendb/FTP/BIRCH/"
FRAMEWORK_URL = BASE_URL+"CURRENT/framework_$TYPE.tar.gz"
MINI_URL = BASE_URL+"minibirch/framework.mini_$TYPE.tar.gz"
BIN_URL = BASE_URL+"CURRENT/bin-$PLATFORM_$TYPE.tar.gz" 
VERSION_URL=BASE_URL+"CURRENT/VERSION"
NEWEST_VERSION=None
	

"""
These methods are called from the java side to set initialize settings
"""

#def set_admin_email(email):
#	ARGS.admin_email=email
#
#def set_disc_install(top_dir):
#	ARGS.set_disc_install()
#	ARGS.top_dir=top_dir
#	
#
#def set_log_dir(logdir):
#	ARGS.set_log_dir(logdir)
#	
#def set_frame_type(frametype):
#	
#	if (frametype.find("D")>=0):
#		ARGS.set_development()
#		
#		
#	if (frametype.find("M")>=0):
#		ARGS.set_mini()
#
#def set_development():
#	ARGS.set_development()
#
#def set_binaries(binaries):
#	
#	
#	for each in binaries:
#		ARGS.add_platform(each)
#
#def set_jython_path(jythonpath):
#	ARGS.jython_path=jythonpath
#
#def set_update():
#	ARGS.is_update=True
#
#def set_make_backup():
#	ARGS.make_backup=True
#
#def set_selected_tag(selected_tag):
#	ARGS.isGitDev=True
#	ARGS.selectedTag=selected_tag
#


