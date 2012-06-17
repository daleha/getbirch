'''
Created on Jun 14, 2010

@modified: Jun 14, 2010
@author: Dale Hamel
@contact: umhameld@cc.umanitoba.ca
'''

from log import *
from setup import *
import javax.swing.JOptionPane as JOptionPane

   


def main( installDir, isUpdate, is_development, platforms, selectedVersion="CURRENT", isWindows=False ):
    info("Running getbirch installer process")

    if (not os.path.lexists(installDir)):
        os.mkdir(installDir)

    if (check_depends()):
        if ( os.path.lexists(installDir) ):
            info('Fetching archives')
            os.chdir(installDir)

            valid = True

            if ( isWindows ):
                from cyglib import setupCygwin
                valid = valid and setupCygwin(installDir)
            valid = valid and getFramework(installDir, is_development)
            valid = valid and getBinaries(installDir, is_development, platforms)
            valid = valid and getInstallScripts( installDir )
            valid = valid and extractInstallScripts( installDir )

            if not valid:
                message = "An error has occurred in fetching the latest version of BIRCH - installion aborted! Please report this problem and email the install log."
                error(mesage)
                JOptionPane.showMessageDialog(None,message );


            #perform the actual install here
    
    shutdown()



if __name__ == "__main__":
    sys.exit(main())
