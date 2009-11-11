@echo off

set GROUP_ID=auctionsniper-lib
set PREVIOUS_PATH=%CD%

cd ../lib

call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=smack -Dversion=3.1.0 -Dpackaging=jar -Dfile=smack-3.1.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=smack -Dversion=3.1.0 -Dpackaging=jar -Dfile=smack-3.1.0-sources.jar -Dclassifier=sources
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=smackx -Dversion=3.1.0 -Dpackaging=jar -Dfile=smackx-3.1.0.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=smackx -Dversion=3.1.0 -Dpackaging=jar -Dfile=smackx-3.1.0-sources.jar -Dclassifier=sources
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=smackx-jingle -Dversion=3.1.0 -Dpackaging=jar -DgeneratePom=true -Dfile=smackx-jingle-3.1.0.jar
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=windowlicker-core -Dversion=rev255 -Dpackaging=jar -Dfile=windowlicker-core-rev255.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=windowlicker-core -Dversion=rev255 -Dpackaging=jar -Dfile=windowlicker-core-rev255-sources.jar -Dclassifier=sources
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=windowlicker-swing -Dversion=rev255 -Dpackaging=jar -Dfile=windowlicker-swing-rev255.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=windowlicker-swing -Dversion=rev255 -Dpackaging=jar -Dfile=windowlicker-swing-rev255-sources.jar -Dclassifier=sources
call mvn install:install-file -DgroupId=%GROUP_ID% -DartifactId=windowlicker-tools -Dversion=rev255 -Dpackaging=jar -Dfile=windowlicker-tools-rev255.jar -DgeneratePom=true

cd %PREVIOUS_PATH%
