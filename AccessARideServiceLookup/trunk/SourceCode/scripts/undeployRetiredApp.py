#======================================================================
# ScriptFile: undeployRetiredApp.py 
# Author : Jay Butler
# Purpose : Un-deploys a retired application from a Weblogic Server
#======================================================================
import sys
import os
import re
from java.lang import System
import getopt


#========================
#Usage Section
#========================
def usage():

    print "Usage:"
    print "java weblogic.WLST undeployApp.py -u username -p password -a adminUrl -n deploymentName -v archiveVersion -t deploymentTarget\n"
    sys.exit(2)

#========================
#Connect To Domain
#========================

def connectToDomain():

    try:
        connect(username, password, adminUrl)
        print 'Successfully connected to the domain\n'

    except:
        print 'The domain is unreacheable. Please try again\n'
        exit()

#========================
#Application State
#========================

def getApplicationState(applicationId, deploymentTarget):

    try:
        domainRuntime()
        cd('AppRuntimeStateRuntime/AppRuntimeStateRuntime')
        currentState = cmo.getCurrentState(applicationId, deploymentTarget)
        print 'Curr State\tApplication'
        print '_______________\t_______________________________'
        print currentState + "\t" + applicationId
        print '\n'
        
        if currentState == 'STATE_RETIRED':
            print '=============================================================================='
            print 'This is the retired version of the application.  Proceeding with un-deploy...\n'
            print 'Starting UnDeployment...\n'
            undeployApplication(applicationId, deploymentTarget)
        else:
            print '\nApplication is not in a proper state for undeployment via this script'
            print 'Only applications in a state of STATE_RETIRED may be undeployed by this script\n'
            
    except:
        print '\n*** Error trying to get state of applicationId: ' +applicationId+ ', Target: ' + deploymentTarget +'\n'
        fnd = 0
        #dumpStack()
        #dumpVariables()
        #exit()
        
        
#========================
#Application undeployment Section
#========================

def undeployApplication(applicationId, deploymentTarget):

    serverConfig()
    
    try:
        print 'UnDeploying... DeploymentName:' +deploymentName+' Targets:'+deploymentTarget+' Version:'+archiveVersion
        undeploy(deploymentName, targets=deploymentTarget, archiveVersion=archiveVersion)

    except:
        print '\n*** Error during the undeployment of ' +applicationId+ '\n'
        dumpStack()
        dumpVariables()
        
#========================
#Input Values Validation Section
#========================

if __name__=='__main__' or __name__== 'main':

    try:
        opts, args = getopt.getopt(sys.argv[1:], "u:p:a:n:v:t:", ["username=", "password=", "adminUrl=", "deploymentName=", "archiveVersion=", "deploymentTarget="])
		
    except getopt.GetoptError, err:
        print str(err)
        usage()

username = ''
password = ''
adminUrl = ''
deploymentName = ''
archiveVersion = ''
deploymentTarget = ''

fnd = 0
currentState = ''

for opt, arg in opts:
    if opt == "-u":
        username = arg
    elif opt == "-p":
        password = arg
    elif opt == "-a":
        adminUrl = arg
    elif opt == "-n":
        deploymentName = arg
    elif opt == "-v":
        archiveVersion = arg
    elif opt == "-t":
        deploymentTarget = arg

if username == "":
    print "Missing \"-u username\" parameter.\n"
    usage()
elif password == "":
    print "Missing \"-p password\" parameter.\n"
    usage()
elif adminUrl == "":
    print "Missing \"-a adminUrl\" parameter.\n"
    usage()
elif deploymentName == "":
    print "Missing \"-n deploymentName\" parameter.\n"
    usage()
#elif archiveVersion == "":
#    print "Missing \"-v archiveVersion\" parameter.\n"
#    usage()
elif deploymentTarget == "":
    print "Missing \"-t deploymentTarget\" parameter.\n"
    usage()

#========================
#Main Control Block For Operations 
#========================

#def undeployMain(applicationId, deploymentTarget):

#	undeployApplication(applicationId, deploymentTarget)
            
            
#========================
#Execute Block
#  This is the sequence of commands being executed by this script
#========================

print '=============================================================================='
print 'Connecting to Admin Server...'
print '=============================================================================='
connectToDomain()

applicationId = deploymentName+'#'+archiveVersion
print 'ApplicationId:' + applicationId + '  Target:' + deploymentTarget
getApplicationState(applicationId, deploymentTarget)
print '=============================================================================='
print 'Execution completed...'
print '=============================================================================='
disconnect()
print '=============================================================================='
print 'Disconnected from Admin server...'
print '=============================================================================='
exit()
