#!/usr/bin/env python
"""
Updates all code files in the Wiki.
Run by: python includeCode.py <code-dir> <wiki-file1> <wiki-file2> ....

Note: You can use globbing to hand over multiple wiki files, e.g. by running the script like this:
python includeCode.py scripts/ wiki/*.wiki 
"""
import os
import sys
import re


def getFiles(dirname, extension):
    for fname in os.listdir(dirname):
        if fname.endswith(extension):
            yield fname, os.path.join(dirname, fname)
        
    

if len(sys.argv) < 3:
    print globals()['__doc__'] % locals()
    sys.exit(1)

codeDir = sys.argv[1]
wikiFiles = sys.argv[2:]

#############################################
# Read all code files
#############################################
programcodeStartLine = '#!/usr/bin/env jython'
replaceTabsWithSpace = 4
codes = {}


for fName, path in getFiles(codeDir, '.py'):
    fIn = open(path, 'r')
    lines = fIn.readlines();
    fIn.close()
    
    #Do some sanity check
    if lines[0].strip() != programcodeStartLine:
        print "Error in file %s - File not started with: %s" % (fName, programcodeStartLine)
        continue
    
    if lines[1].replace(' ', '').startswith('#Filename:'):
        fileNameInComment = lines[1][lines[1].find(':')+1:].strip()
        if fileNameInComment != fName:
            raise ValueError("Error in file %s - Filename in comment (%s) does not match actual filename" % (fName, fileNameInComment))        
    else:
        lines.insert(1, '# Filename: %s\n' % fName)
    
    content = "".join(lines)
    if replaceTabsWithSpace > 0:
        content = content.replace('\t', ' '*replaceTabsWithSpace)
    codes[fName] = content



#############################################
# Read all wiki files
#############################################
for path in wikiFiles:
    fName = os.path.basename(path)
    fIn = open(path, 'r')
    lines = fIn.readlines()
    fIn.close
    
    content = ""
    codeBlockBegin = False
    lineIdx = 0
    while lineIdx < len(lines):
        line = lines[lineIdx]
        content += line
        if line.startswith('{{{'):            
            nextLineIdx = lineIdx+1
            if lines[nextLineIdx].strip() == programcodeStartLine: #Skip program code start line
                nextLineIdx += 1
            if lines[nextLineIdx].replace(' ', '').startswith('#Filename:'):
                codeName = lines[nextLineIdx][lines[nextLineIdx].find(':')+1:].strip()
                if codeName not in codes:
                    print "%s - Code %s not found!!!!!!!!!!!!!!" % (fName, codeName)
                    content += "#Filename: %s" % codeName
                else:
                    content += codes[codeName]
                    print "%s - include script: %s" % (fName, codeName)
                
                #Scan until ending }}}
                while lines[nextLineIdx].strip() != '}}}':
                    nextLineIdx += 1
                
                content += '\n}}}\n'
                lineIdx = nextLineIdx
        
        lineIdx += 1  
    
    fOut = open(path, 'w')
    lines = fOut.write(content)
    fOut.close
    #print content 
                
print "DONE"
                
                
            
            
        
        
    


