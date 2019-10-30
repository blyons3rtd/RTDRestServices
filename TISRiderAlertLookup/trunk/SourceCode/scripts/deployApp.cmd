@ECHO OFF

cd scripts
CALL "setEnv.cmd"

@REM Expected arguments:
@REM %1 = WebLogic Admin User
@REM %2 = WebLogic Admin Password
@REM %3 = Admin URL
@REM %4 = Application Name
@REM %5 = Deployment EAR/WAR
@REM %6 = Application Version
@REM %7 = Deployment Target (e.g.; Server, Cluster)

java weblogic.WLST -i deployApp.py -u %1 -p %2 -a %3 -n %4 -f %5 -v %6 -t %7





