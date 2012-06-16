'''
Created on Jun 14, 2010

@modified: Jun 14, 2010
@author: Dale Hamel
@contact: umhameld@cc.umanitoba.ca
'''

from log import *
from setup import *

#def disc_prepare():
#   
#   binaries= tarfile.open(ARGS.top_dir+"/binaries.tar.gz")
#   
#   def untar_binary_tar(name):
#       print_console("Extracting "+name+ " to "+ARGS.install_dir+"/"+name)
#       binaries.extract(name,ARGS.install_dir)
#   
#   print_console("This is a disc install, must decompress binaries to install directory")
#   
#   print_console("Copying framework to installation directory...")
#   shutil.copy(ARGS.top_dir+"/framework.tar.gz", ARGS.install_dir)
#   
#   print_console("Copying binaries to installation directory...")
#   
#   
#   for each in ARGS.platforms:
#       
#       untar_binary_tar(each+".tar.gz")
    
    


    


def main( installDir, isUpdate, is_development, platforms ):
    info("Running getbirch installer process")

    if ( os.path.lexists(installDir) ):
        info('Fetching archives')
        os.chdir(installDir)
        get_framework(installDir, is_development)
        get_binaries(installDir, is_development, platforms)


    if (not isUpdate):
        
        clobber_check(installDir)
        extract_tarballs(installDir, platforms)
        #install()
    
        
#       if (ARGS.disc_install):
#           disc_prepare()
#           fetch = False




    else:
#call update
        #update()
    
    shutdown()



if __name__ == "__main__":
    sys.exit(main())
