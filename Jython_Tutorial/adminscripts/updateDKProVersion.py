#!/usr/bin/env python
"""
Updates dkpro components to a certain version
Run by: python updateDKProVersion.py <new-dkpro-version> <script1> <script2> ....

Note: You can use globbing to hand over multiple scripts files, e.g. by running the script like this:
python updateDKProVersion.py <new-dkpro-version> scripts/*.py
"""
import os
import sys
import re

if len(sys.argv) < 3:
    print globals()['__doc__'] % locals()
    sys.exit(1)

newDKProVersion = sys.argv[1]
scriptFiles = sys.argv[2:]



def updateVersion(newDKProVersion, lines):
    for lineIdx in xrange(len(lines)):
        line = lines[lineIdx]
        if line.startswith("require('de.tudarmstadt.ukp.dkpro.core"):
            versionBegin = line.rfind(':')
            versionEnd = line.find("'", versionBegin)
            lines[lineIdx] = line[0:versionBegin]+":"+newDKProVersion+line[versionEnd:]


for path in scriptFiles:
    fIn = open(path, 'r')
    lines = fIn.readlines();
    fIn.close()
    updateVersion(newDKProVersion, lines)
    
    fOut = open(path, 'w')
    fOut.write("".join(lines))
    fIn.close()
    print "%s updated" % os.path.basename(path)