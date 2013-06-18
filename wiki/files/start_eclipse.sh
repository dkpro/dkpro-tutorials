#!/bin/sh

script_dir=$(cd $(dirname $0); pwd;)

# Your MySQL settings, the default port is 3306
export MYSQL=localhost:3306

# Your Maven specific options: Java-style options which apply when Maven is un
# e.g. -Xmx256m -Xms128m
export MAVEN_OPTS=-Xmx256m

# Path to your DKPro instance. 
# Will also be used for resources needed by DKPro components
export DKPRO_HOME=<path to your DKPro instance/data>

${script_dir}/eclipse
