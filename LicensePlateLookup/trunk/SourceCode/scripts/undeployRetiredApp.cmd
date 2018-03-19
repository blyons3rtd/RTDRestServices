ECHO OFF

cd scripts
CALL "setEnv.cmd"

@REM Expected arguments:
@REM %1 = WebLogic Admin User
@REM %2 = WebLogic Admin Password
@REM %3 = Admin URL
@REM %4 = Application Name
@REM %5 = Application Version
@REM %6 = Deployment Target (e.g.; Server, Cluster)

java weblogic.WLST -i undeployRetiredApp.py -u %1 -p %2 -a %3 -n %4 -v %5 -t %6





