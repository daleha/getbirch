import os,shutil,urllib

from util import wget
from util import untar
from util import shutdown

from log import *
from Globals import *
        
#from cygcfg import cygwin_untar
#from cygcfg import cygwin_exec
import javax.swing.JOptionPane as JOptionPane

"""
Verify that the dependancies are present
"""
    
def check_depends():
    def check_depend(depend):
        #if the dependency is found, then this will evaluate to true, else, false
        found= os.popen("which "+depend).read().strip() !=""
        
        info("Dependancy \""+depend+"\" found: "+str(found))
        if (not found):
            return False
        else:
            return True
    
    dependancies=["csh","java","python"]
    foundall=True
    
    for each in dependancies:
        foundall=check_depend(each) 
        if (not foundall):
            error("Dependancy " + each+ " not found on system path!")
            return False
        
    return foundall


def clobber_check(installDir):

        
        if (os.path.lexists(installDir)):
                contents=os.listdir(installDir)
                
                if (contents.count("local")!=0 or os.path.exists(installDir+"/admin/BIRCH.properties")):
                    error("The directory specified already contains a BIRCH installation! Aborting")
                    JOptionPane.showMessageDialog(None,"Installation cancelled to prevent clobbering existing installation.\nPlease restart installer and select a different directory, or remove old installation")
                    shutdown()
        else:
            check=os.popen("echo $BIRCH").read().strip()
            if (check!=None and check!=""):

                info("An existing BIRCH installation was found at:\""+check+"\"")

            
                if (os.path.lexists(check) and os.path.samefile(check, installDir)):
                    message="The installation directory that you specified:\n"+installDir+"\nAlready contains a BIRCH installation. Are you sure you wish to proceed?(NOT recommended)"
                    reload = JOptionPane.showConfirmDialog(None,message, "Input",JOptionPane.YES_NO_OPTION);
                
                    if (reload==JOptionPane.NO_OPTION):
                        info("User aborted install.")
                        shutdown()
                    
                    else:
                        info("User decided to install over old installation.")
        return      

"""
Fetches the framework of the type depending on which flags were set by the GUI
"""
def get_framework(installDir,is_development):
        
        url=None
        
        os.chdir(installDir)

        
        if (not is_development):
            from detect import detect_latest_version
            
            url=FRAMEWORK_URL
            url=url.replace("$TYPE",detect_latest_version())
            info("Downloading birch framework to " + installDir + " from " + url)
            
       # elif (not is_development and is_mini):
       #     
       #     url=ARGS.MINI_URL
       #     url=url.replace("$TYPE",ARGS.NEWEST_VERSION)
       #     info( "Downloading miniBirch framework to " + installDir + " from " + url)
        
        elif(is_development ):
            
            url=FRAMEWORK_URL
            url=url.replace("$TYPE","D")
            url=url.replace("CURRENT","Development")
            info( "Downloading development framework to " + installDir + " from " + url)
            
       # elif(is_development and is_mini):
       #     url=ARGS.MINI_URL
       #     url=url.replace("$TYPE","D")
       #     url=url.replace("CURRENT","Development")
       #     info( "Downloading miniBirch development framework to " + installDir + " from " + url)
    
        
        wget(url, "framework.tar.gz",CONSOLE)

"""
Fetches all binaries selected by the GUI
"""
def get_binaries(installDir, is_development, platforms):
    
    os.chdir(installDir)
    
    def get_binary(platform):
        url = BIN_URL
        url=url.replace("$PLATFORM",platform)
        
        if (is_development):
            url=url.replace("$TYPE","D")
            url=url.replace("CURRENT","Development")
        else:
            from detect import detect_latest_version
            url=url.replace("$TYPE",detect_latest_version())
            
        info( "Downloading binaries for platform \"" + platform  + "\" to " + installDir + " from " + url)
        wget(url, platform+".tar.gz",CONSOLE)
        
    for each in platforms:
        get_binary(each)

    
def extract_tarballs(installDir, platforms, windows=False):
    def extract_binary(binary):
        if (os.path.lexists(installDir +"/"+binary+ ".tar.gz")):
            #if (windows):
            #   cygwin_untar(ARGS.install_dir +"/"+binary+ ".tar.gz",binary+ ".tar.gz")

            #else:
            untar(binary+ ".tar.gz")    
        else:
            error( "The required file "+binary+ ".tar.gz was not found")
    
    
    
    info( "Decompressing framework...")
    os.chdir(installDir)
    
    if (os.path.lexists(installDir + "/framework.tar.gz")):

        #if (windows ):
        #   cygwin_untar(ARGS.install_dir + "/framework.tar.gz","framework.tar.gz")
        #else:
        untar("framework.tar.gz")
    else:
        error( "The required file framework.tar.gz was not found")
        return

    info( "Decompressing binaries...")
    
    for each in platforms:
        extract_binary(each)
    
