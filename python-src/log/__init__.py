
from Globals import CONSOLE

import logging
from datetime import datetime

def initLogger():
    d = datetime
    t = d.today()
    datestr = t.strftime("%y_%m_%d_%H_%M_%S")
    logging.basicConfig(filename="getbirch_log_"+datestr+".log", level=logging.INFO)

def label(label):
    message= 20*"*"+label+20*"*"
    logging.info(message) 
    printConsole(message)



def debug(message):
    logging.debug(message) 
    printConsole(message)


def info(message):
    logging.info(message) 
    printConsole(message)


def error(message):
    logging.error(message) 
    printConsole(message)


def printConsole(line):
	if line==None:
		return
	line=line.strip().replace("//","/")
	CONSOLE.writeLine(line)
	
	print(line)


