import os
import datetime
from util import quote_dos_path


class Arguments:
	DOMAIN="http://birchlabs.dyndns-server.com"#The domain of the BIRCH gitweb server, note that there must be an apache alias to the directory containing the git repository, so that we can grab the head reference

	HEAD_PROJECT="psgendb.git"
	HEADREV_URL =DOMAIN+"/gitdir/"+HEAD_PROJECT+"/refs/heads/master"		
	BASE_URL="http://www.umanitoba.ca/faculties/afs/plant_science/psgendb/FTP/BIRCH/"
	FRAMEWORK_URL = BASE_URL+"CURRENT/framework_$TYPE.tar.gz"
	MINI_URL = BASE_URL+"minibirch/framework.mini_$TYPE.tar.gz"
	BIN_URL = BASE_URL+"CURRENT/bin-$PLATFORM_$TYPE.tar.gz" 
	VERSION_URL=BASE_URL+"CURRENT/VERSION"
	GITDEV_URL="http://birchlabs.dyndns-server.com/gitweb/?"
	GITDEV_HASHMAP={"p":"framework.git","a":"snapshot","h":"SHA","sf":"tgz"}
	NEWEST_VERSION=None
	
	instance =None


	def __init__(self, platform=None,platforms=None,install_dir=None,multi_bins=False,is_development=False,is_mini=False):
			
		self.platform = platform
		self.platforms=platforms #binaries to get if multiple specified
		self.multi_bins=multi_bins# get multiple binaries
		self.is_development=is_development
		self.is_mini=is_mini
		self.install_dir=install_dir
		self.log_dir=None
		self.log_file=None
		self.install_log=None
		self.returncode=None
		self.disc_install=False
		self.top_dir=None
		self.is_update=False
		self.jython_path=None
		self.make_backup=False
		self.isGitDev=False
		self.selectedTag=None
	
		instance=self

	def set_disc_install(self):
		self.disc_install=True
					
	def set_platform(self,platform):
		self.platform=platform
		if (self.platforms==None):
			self.platforms=list()
		self.platforms.append(platform)
	
	def add_platform(self,platform):
		if (self.platforms != None):
			self.platforms.append(platform)

		else:
			self.platforms= list()
			self.platforms.append(platform)

		
		self.multi_bins=True
	
	def set_mini(self):
		self.is_mini=True
	
	def set_development(self):
		self.is_development=True
	
	def set_installdir(self,installdir):
		self.install_dir=installdir
		
		if (not os.path.lexists(self.install_dir)):
			os.mkdir(self.install_dir)
		if "winxp-32" in self.platform:
			self.install_dir=quote_dos_path(self.install_dir)
			self.CYGWIN_DIR=self.install_dir+"/cygwin/"
			self.CYGPAK_DIR=self.install_dir+"/cygpak/"
		
	def set_log_dir(self,logdir):
		
		if "winxp-32" in self.platform:
			self.log_dir=quote_dos_path(logdir)
		else:
			self.log_dir=logdir
		
		name=self.log_dir+"/birch_install_log"+(str(datetime.datetime.now())).replace(":",".").replace(" ","")+".log"
		self.log_file=name
		self.install_log= open (name,"w")


