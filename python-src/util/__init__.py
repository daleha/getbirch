import os
import sys
import urllib
import urllib2
import tarfile
import shutil
from subprocess import Popen
from subprocess import PIPE
from threading  import Thread

try:
	from Queue import Queue, Empty
except ImportError:
	from queue import Queue, Empty  # python 3.x



ON_POSIX = 'posix' in sys.builtin_module_names



from log import *

from Globals import CONSOLE

#import javax.swing.JOptionPane as JOptionPane




   


"""
stream_exec executes the command "command" using subprocess.Popen, setting
the cwd for execution to "path". If "callback" is set to a function pointer, 
then on each line of output a callback function is executed. "verbose" is a flag 
that determines if the output of the command should be sent to the console.
By default, output is sent to console.

Stream exec uses a message queue to prevent blocking and deadlock when doing 
cross-platform execution calls. This is particularly an issue when doing
filesystem calls on Windows, which is why this method was created.
"""
def stream_exec(command,path=None,verbose=True,callback=None):


	def enqueue_output(out, queue):
		for line in iter(out.readline, ''):
			queue.put(line)
		out.close()

	def read_output():
		# read line without blocking
		try:  line = q.get_nowait() # or q.get(timeout=.1)
		except Empty:
			return None
		else: # got line
			return line	

	cwd=os.getcwd()
	if (path!= None and os.path.isdir(path)):
		debug("Changing directory to " + path)
		os.chdir(path)


	proc = Popen(command,shell=True, stdout=PIPE, bufsize=-1, close_fds=ON_POSIX)
	debug("Dispatched command \""+command+"\"")
	q = Queue()
	t = Thread(target=enqueue_output, args=(proc.stdout, q))
	t.daemon = True # thread dies with the program
	t.start()
	
	output = ""
	while (proc.poll()==None):
		line=read_output()
		if (line!=None):
			if (verbose):
				print_console(line)
			if (callback!=None):
				callback()
			output=output+line
	os.chdir(cwd)
	return output
	


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



def untar(file, path=".",noroot=False,exclude=list()  ):
		"""Extracts the tarfile given by file to current working directory by default, or path"""

        cwd = os.getcwd()
        if( os.path.isdir(path))
            os.chdir(path)

		tarball = tarfile.open(file)
		info("Calculating tar info, this may take some time")
		tarInfo=tarball.getmembers()

		total=len(tarInfo)
		count=0
		CONSOLE.setProgress(0)
		info("Beginning the extraction process")

        if (not native):
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

        os.chdir(cwd)



def shutdown():
	info("Shutting down")
	CONSOLE.shutdown()
	
