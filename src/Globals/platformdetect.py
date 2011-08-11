
import os
import sys
import subprocess
from subprocess import Popen



PROGRAM = "platformdetect.py: "



class PlatformDetector:
	"""A class to execute a series of probes in order to determine what the system platform is"""
	
	def __init__(self,probe_dir):
		self.platform=None
		self.probe_dir=probe_dir
		
	
	def probe(self, to_probe):
		"""
		Attempts to execute the probe for platform given by to_probe in directory probe_dir.
		@probe_dir: the directory where the probes should be located
		@mode: if this is "quiet" it will suppress output
		@to_probe: the platform to probe for
		"""
		os.chdir(self.probe_dir)
		print(PROGRAM + "Probing os: " + to_probe)
	 	probe_str = self.probe_dir + "probe_" + to_probe 

	  	print(PROGRAM + "Running probe: " + probe_str)
	  	probe_str = probe_str + " 2>/dev/null"
	   	os.system(probe_str)
	
	def make_win_path(self,unix_path):
		win_path= unix_path.replace("/","\\")
		win_path = win_path.replace(" ","^ ")
		
		return win_path
	
	def win_probe(self, to_probe):
 		mode="loud"
 		#probe_dir=self.make_win_path(probe_dir)
 		
		os.chdir(self.probe_dir)
		print(PROGRAM + "Probing os: " + to_probe)
	 	probe_str = self.probe_dir + "probe_" + to_probe 

	  	print(PROGRAM + "Running probe: " + probe_str)

	   	os.system(probe_str)
 	
 	
	def run_probes(self):
		"""
		Runs the probes for all platforms BIRCH will work on.
		@probe_dir: the directory where the probes should be located
		@mode: if this is "quiet" it will supress output
		"""
		
		
		os.system("chmod +x " + self.probe_dir + "/*")
		
		#Note: windows probes MUST come first, in case if a gnu/linux system has WINE installed.
		self.win_probe( "win32.exe")
		self.probe("linux32")
	 	self.probe("linux64")
	  	self.probe("osx")
   	   	self.probe("sunsparc")
	   	self.probe("sun64")
	   	

	def get_result(self):
		"""
		Reads the "result" file, and returns what the current platform is
		"""
		os.chdir(self.probe_dir)
		result_path = self.probe_dir + "/result"
		
		if (os.path.exists(result_path)):
			result = open("result", "r")
	 		to_return = result.read()
	 		print(PROGRAM + "The platform is \"" + to_return + "\"")
	 		
	 		self.result=to_return
	 		
	 		if (to_return.find("win")>=0):
	 			os.system("del result")
	 		else:
				 os.system("rm result")
			
		else:
			print(PROGRAM + "An error occurred detecting platform")
			to_return = None
			
	 	return to_return

	def detect(self ):
		"""
		Runs the detection probes, then returns the platform to caller
		@mode: if this is "quiet" it will suppress messages
		"""
		self.run_probes()
		result = self.get_result()
		return result

	def detect_platform(self):
		

		result = self.detect()
	
		PLATFORM = None
		if result == "linux32":
			print(PROGRAM + "Setting platform to \"linux-intel\" ")
			PLATFORM = "linux-intel"
	
		elif result == "linux64":
			print(PROGRAM + "Setting platform to \"linux-x86_64\" ")
			PLATFORM = "linux-x86_64"

		elif result == "osx":
			print(PROGRAM + "Setting platform to \"osx-x86_64\" ")
			PLATFORM = "osx-x86_64"
	
		elif result == "sunsparc":
			print(PROGRAM + "Setting platform to \"solaris-sparc\" ")
			PLATFORM = "solaris-sparc"
	
		elif result == "sun64":
			print(PROGRAM + "Setting platform to \"solaris-amd64\" ")
			PLATFORM = "solaris-amd64"
			
		elif result == "win32":
			print(PROGRAM + "Setting platform to \"winxp-32\" ")
			PLATFORM = "winxp-32"
	
		return PLATFORM
