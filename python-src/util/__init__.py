from re import *
import urllib
import urllib2
import tarfile
import shutil

import os
import sys

from log import *

from Globals import CONSOLE

#import javax.swing.JOptionPane as JOptionPane



"""
A wrapper function to print a label for a log section.
"""
	
"""
Fetches a file from the specified "url", saving it as "name".
If a console object handle is passed in, it will print the current
download status to console.
"""
def wget( url, name,console=None):
		"""Downloads from url specified to the filename/path specified and displays progress"""
		print("Fetching "+name+" from "+url+"\n\n")
		#console update callback
		def progresshook(numblocks, blocksize, filesize, url=None):
			
			try:
				percent = min((numblocks * blocksize * 100) / filesize, 100)
			except:
				percent = 100
			if numblocks != 0:
				
				
				MB_CONST = 1000000 # 1 MB is 1 million bytes
				out_str =  "Progress:" + str(percent) + '%' + " of " + str(filesize / MB_CONST) + "MB\r"
				
				if (console==None):

					sys.stdout.write("\r"+out_str)
				else:
					if (filesize>0):
						console.setProgress(percent)
					else:
						console.setIndeterminate(True)
						bytecount=numblocks*blocksize;
						progstr="Downloaded: "+str(bytecount/MB_CONST)+" MB ("+str(bytecount)+" bytes) of ~200MB"
						console.setProgressString(progstr)
		CONSOLE.setProgress(0)
		urlStream = urllib.urlretrieve(url, name, progresshook)
		CONSOLE.setIndeterminate(False)
		CONSOLE.setProgressString(None)
		CONSOLE.hideProgress()



def untar(file, path=".",noroot=False,exclude=list()):
		"""Extracts the tarfile given by file to current working directory by default, or path"""


		tarball = tarfile.open(file)
		info("Calculating tar info, this may take some time")
		tarInfo=tarball.getmembers()

		total=len(tarInfo)
		count=0
		CONSOLE.setProgress(0)
		info("Beginning the extraction process")
		for each in tarInfo:
			if (not each.name in exclude):
				count=count+1
				percent=int((float(count)/total)*100)	
				item=list()
				item.append(each)
				tarball.extractall(members=item)
				CONSOLE.setProgress(percent)
			else:
				info("Excluded member "+each.name)

		if (noroot==True):
			if (tarInfo[0].name.find("pax_global_header")>=0):
				info("Trimming global header")
				tarInfo.pop(0)
			rootpath=tarInfo.pop(0)
			info("Using "+rootpath.name+" as root tar dir.")

			contents=os.listdir(rootpath.name)
			for each in contents:
				info("Moving "+each+" to rebased root path.")
				shutil.move(rootpath.name+"/"+each,os.getcwd()+"/"+each)

			shutil.rmtree(rootpath.name)
			
		tarball.close()
		CONSOLE.hideProgress()




#def quote_dos_path(path):

#	path=path.replace("/","\"/\"").replace(":\"",":")
#	if path[len(path)-2]=="/":
#		path=path[0:len(path)-2]
#	if path[0]=="/":
#			path=path[1:]
#	return path


def convert_pathsep(path):
	path=path.replace("\\","/")
	return path
 

def quote_dos_path(path):

	def quotePath(match):
		return "/\""+match.group(1)+"\""

	path=path.replace("\\","/")
	cleanpath= sub ("/(.*?\s.*?[^/]*)",quotePath ,path)

	return cleanpath


def shutdown():
	info("Shutting down")
	CONSOLE.shutdown()
	
