from re import *
import urllib
import urllib2
import tarfile
import shutil

import os
import sys


import traceback

import javax.swing.JOptionPane as JOptionPane



"""
A wrapper function to print a label for a log section.
"""
def print_label(label):
	rad=20
	string=rad*"*"+label+":"+rad*"*"
	print_console(string)	


def print_console(line):
	if line==None:
		return
	line=line.strip().replace("//","/")
	line=PROGRAM+" "+line
	CONSOLE.writeLine(line)
	
	if (line!="" and ARGS!=None and ARGS.install_log!=None):
		ARGS.install_log.write(line+"\n")
		ARGS.install_log.flush()
	print(line)

	
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
		print_console("Calculating tar info, this may take some time")
		info=tarball.getmembers()

		total=len(info)
		count=0
		CONSOLE.setProgress(0)
		print_console("Beginning the extraction process")
		for each in info:
			if (not each.name in exclude):
				count=count+1
				percent=int((float(count)/total)*100)	
				item=list()
				item.append(each)
				tarball.extractall(members=item)
				CONSOLE.setProgress(percent)
			else:
				print_console("Excluded member "+each.name)

		if (noroot==True):
			if (info[0].name.find("pax_global_header")>=0):
				print_console("Trimming global header")
				info.pop(0)
			rootpath=info.pop(0)
			print_console("Using "+rootpath.name+" as root tar dir.")

			contents=os.listdir(rootpath.name)
			for each in contents:
				print_console("Moving "+each+" to rebased root path.")
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
	print_console("Shutting down")
	try:	
		cleanup()
	except Exception, err:
		print_console(err.message)
		err=traceback.format_exc()
		print_console(err)
	CONSOLE.shutdown()
	


from Globals import CONSOLE
from Globals import ARGS
from Globals import PROGRAM

