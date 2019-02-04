#!/bin/sh
#
# Copyright © 2013/2014, Veljko Zivkovic
# All rights reserved.
#
# No portion of this file may be reproduced in any form, or by any means, without the prior written
# consent of the author.
#


TIRION_HOME=`pwd`
export TIRION_HOME

mkdir -p $TIRION_HOME/logs/
mkdir -p $TIRION_HOME/logs/metrics/
mkdir -p $TIRION_HOME/work/pool/
mkdir -p $TIRION_HOME/work/compiler/src/
mkdir -p $TIRION_HOME/work/compiler/bin/

# add third party JARs to classpath
for i in `ls $TIRION_HOME/lib/*.jar`; do
        CLASSPATH=$i:$CLASSPATH
done

CLASSPATH=$CLASSPATH:$TIRION_HOME/conf/
export CLASSPATH

JVM_ARGS="-server -XX:MaxDirectMemorySize=512M -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xms3000M -Xmx3000M -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=50001 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false"
export JVM_ARGS

java -Dlog4j.configuration=log4j.properties $JVM_ARGS -classpath $CLASSPATH com.tirion.db.TirionDbMain "$@"