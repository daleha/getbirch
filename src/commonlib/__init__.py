
import urllib
import urllib2
import os
import shutil
import hashlib
import sys
import traceback
import tarfile

from subprocess import Popen
from subprocess import PIPE

from threading  import Thread

try:
	from Queue import Queue, Empty
except ImportError:
	from queue import Queue, Empty  # python 3.x


import javax.swing.JOptionPane as JOptionPane

ON_POSIX = 'posix' in sys.builtin_module_names


"""
A wrapper function to print a label for a log section.
"""
def print_label(label):
	rad=20
	string=rad*"*"+label+":"+rad*"*"
	print_console(string)	

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
		print_console("Changing directory to " + path)
		os.chdir(path)


	proc = Popen(command,shell=True, stdout=PIPE, bufsize=-1, close_fds=ON_POSIX)
	print_console("Dispatched command \""+command+"\"")
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


def shutdown():
	print_console("Shutting down")
	try:	
		cleanup()
	except Exception, err:
		print_console(err.message)
		err=traceback.format_exc()
		print_console(err)
	CONSOLE.shutdown()
	
	
def cleanup():
	def cygwin_rm(path):
		cygwin_exec("rm "+path)

	def remove_tarball(prefix,rm_func):
		def try_remove(rm_func,path):
			if (os.path.lexists(path)):
				try:
					rm_func(path)
				except:
					print_console("Could not remove "+path)

		try_remove(rm_func,ARGS.install_dir+"/"+prefix+".tar.gz")
		try_remove(rm_func,ARGS.install_dir+"/"+prefix+".tar")
	

	if "winxp-32" in ARGS.platform:
		rm_func=cygwin_rm
	else:
		rm_func=os.remove
	
	remove_tarball("framework",rm_func)
	remove_tarball("cyghead",rm_func)
	
	if (not ARGS.multi_bins):
		remove_tarball(ARGS.platform,rm_func)
	else:
		for each in ARGS.platforms:
			remove_tarball(each,rm_func)
	
	if (ARGS.install_log!=None):
		ARGS.install_log.flush()
		ARGS.install_log.close()



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
	

def untar(file, path=".",noroot=False):
		"""Extracts the tarfile given by file to current working directory by default, or path"""


		tarball = tarfile.open(file)
		print_console("Calculating tar info, this may take some time")
		info=tarball.getmembers()
		if (noroot==True):
			print_console("Removing root entry")
			if (info[0].name.find("pax_global_header")>=0):
				print_console("Trimming global header")
				info.pop(0)
			rootpath=info.pop(0)
			print_console("Extracting to root directory \""+str(rootpath.name)+"\"")
		total=len(info)
		count=0
		CONSOLE.setProgress(0)
		print_console("Beginning the extraction process")
		for each in info:
				
			count=count+1
			percent=int((float(count)/total)*100)	
			item=list()
			item.append(each)
			tarball.extractall(members=item)
			CONSOLE.setProgress(percent)

		if (noroot==True):
			contents=os.listdir(rootpath.name)
			for each in contents:
				print_console("Moving "+each+" to rebased root path.")
				shutil.move(rootpath.name+"/"+each,os.getcwd()+"/"+each)

			shutil.rmtree(rootpath.name)
			
		tarball.close()
		CONSOLE.hideProgress()


from Globals import CONSOLE
from Globals import ARGS
from Globals import PROGRAM

