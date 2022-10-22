#!/bin/bash
PWDPATH=`dirname $0`
PORTAL_HOME=`cd $PWDPATH && cd .. && pwd`
echo $PORTAL_HOME
cd $PORTAL_HOME
JVM_OPTS="
-server
 -Xms2g
 -Xmx4g
 -XX:NewSize=2048m
 -XX:SurvivorRatio=6
 -XX:+AlwaysPreTouch
 -XX:+UseG1GC
 -XX:MaxGCPauseMillis=2000
 -XX:GCTimeRatio=4
 -XX:InitiatingHeapOccupancyPercent=30
 -XX:G1HeapRegionSize=16M
 -XX:ConcGCThreads=2
 -XX:G1HeapWastePercent=10
 -XX:+UseTLAB
 -XX:+ScavengeBeforeFullGC
 -XX:+DisableExplicitGC
 -XX:+PrintGCDetails
 -XX:-UseGCOverheadLimit
 -XX:+PrintGCDateStamps
 -Xloggc:logs/gc.log
"

start() {
nohup java $JVM_OPTS -Dlogging.config=$PORTAL_HOME/config/log4j2-spring.xml -jar $PORTAL_HOME/lib/cactus-1.0.0.jar &
echo -e '\r'
}
logs_dir=$PORTAL_HOME/logs
if [ ! -d "$logs_dir" ]; then
        mkdir $logs_dir
fi
touch $PORTAL_HOME/logs/console.log
start >> $PORTAL_HOME/logs/console.log 2>> $PORTAL_HOME/logs/console.log
