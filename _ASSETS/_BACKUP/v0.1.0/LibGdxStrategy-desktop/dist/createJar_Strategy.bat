
: change to eclipse output
  cd ..\bin
  
: copy base classes
  xcopy /Y /S ..\..\LibGdxStrategy\bin .
  
: package jar  
  jar cvMf Strategy_unsigned.jar de/ data/ META-INF/

: change back to dist dir and copy packaged jar
  cd ..\dist
  move ..\bin\Strategy_unsigned.jar Strategy_unsigned.jar

: sign
  jarsigner -keystore E:\eclipse_workspace\LibGdxStrategy\trunk\LibGdxStrategy-desktop\dist\keystore.jks -storepass chrisy -keypass chrisy -signedjar Strategy.jar Strategy_unsigned.jar Shooter

: delete unsigned
  del Strategy_unsigned.jar
  
: paws
  pause


: HOW TO create a keystore before signing
: keytool -genkey -alias Shooter -keyalg RSA -keystore E:\eclipse_workspace\Shooter\trunk\dist\keystore.jks -keysize 2048 -validity 100000
