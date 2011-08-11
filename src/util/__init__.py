from re import *

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
