#========================================================
# ScriptFile: deployApp.py 
# Author : Jay Butler
# Purpose : Deploys an application WAR or EAR to a Weblogic Server
#========================================================
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
    print "java weblogic.WLST deployApp.py -u username -p password -a adminUrl -n deploymentName -f deploymentFile -v archiveVersion -t deploymentTarget\n"
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
        exit(exitcode=101)

#========================
#Applications deployment Section
#========================

def deployApplication():

    try:
        print 'Deploying application ' +deploymentName+ ' ' +archiveVersion+'...\n'
        progress=deploy(deploymentName, deploymentFile, targets=deploymentTarget, upload='true')
        print '\n'
        if archiveVersion == '':
            print 'startApplication:'+deploymentName
            progress=startApplication(deploymentName)
        else:
            print 'startApplication:'+deploymentName+' Version:'+archiveVersion
            progress=startApplication(deploymentName, archiveVersion=archiveVersion)

    except:
        print 'Error during the deployment of ' +deploymentName+ ' ' +archiveVersion+ '\n'
        dumpStack()
        dumpVariables()
        exit(exitcode=101)
		
#========================
#Input Values Validation Section
#========================

if __name__=='__main__' or __name__== 'main':

    try:
        opts, args = getopt.getopt(sys.argv[1:], "u:p:a:n:f:v:t:", ["username=", "password=", "adminUrl=", "deploymentName=", "deploymentFile=", "archiveVersion=", "deploymentTarget="])
		
    except getopt.GetoptError, err:
        print str(err)
        usage()

username = ''
password = ''
adminUrl = ''
deploymentName = ''
deploymentFile = ''
archiveVersion = ''
deploymentTarget = ''

for opt, arg in opts:
    if opt == "-u":
        username = arg
    elif opt == "-p":
        password = arg
    elif opt == "-a":
        adminUrl = arg
    elif opt == "-n":
        deploymentName = arg
    elif opt == "-f":
        deploymentFile = arg
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
elif deploymentFile == "":
    print "Missing \"-f deploymentFile\" parameter.\n"
    usage()
elif deploymentTarget == "":
    print "Missing \"-t deploymentTarget\" parameter.\n"
    usage()

#========================
#Main Control Block For Operations
#========================

def deployMain():
    
    appVersionString = deploymentName+'#'+archiveVersion
    print 'Current deployed apps...\n'
    appList = re.findall(deploymentName, ls('/AppDeployments/'))
    print(appList)
    if len(appList) > 1:
        print 'Looking for existence of ' + appVersionString + '...'
        verList = re.findall(appVersionString, ls('/AppDeployments'))
        if len(verList) == 0:
            print 'There are already two previous versions of this application installed'
            print 'In order to install version ' + archiveVersion + ' the current retired version must first be deleted'
            exit()
    
    if len(appList) >= 1:
        print 'Application '+deploymentName+' Found on server '
        print appList
        print '=============================================================================='
        print 'Application Already Exists!\n'
        print 'Updating Application '+deploymentName+' '+deploymentTarget+'...'
        print '=============================================================================='
        deployApplication()
        print '=============================================================================='
        print 'Revised Application Deployed!  Activating changes...'
        #save()
        #activate()
        print 'Changes Activated!'
        print '=============================================================================='
    else:
        print '=============================================================================='
        print 'No application with same name found!'
        print 'Deploying new Application '+deploymentName+' on'+deploymentTarget+' server...'
        print '=============================================================================='
        deployApplication()
        print '=============================================================================='
        print 'Application Deployed!  Activating changes...'
        #save()
        #activate()
        print 'Changes Activated!'
        print '=============================================================================='
        
        
#========================
#Execute Block
#  This is the sequence of commands being executed by this script
#========================
print '=============================================================================='
print 'Connecting to Admin Server...'
print '=============================================================================='
connectToDomain()
print '=============================================================================='
print 'Starting Deployment...'
print '=============================================================================='
#edit()
#startEdit()
deployMain()
#save()
#activate()
print '=============================================================================='
print 'Execution completed...'
print '=============================================================================='
disconnect()
print '=============================================================================='
print 'Disconnected from Admin server...'
print '=============================================================================='
exit()
