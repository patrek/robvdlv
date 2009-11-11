________________________________________________________________________________________________________________________
Setup project in your favorite IDE
¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
- Run "install-external-libs-to-maven-repository.cmd" to add all
  required non-available-in-public-maven2-repositories dependencies to your local maven repository.
- Open pom.xml as project in your IDE.

________________________________________________________________________________________________________________________
Install Openfire 3.6.4
¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
- Download at: http://www.igniterealtime.org/downloads/index.jsp
- After installation you can login with 'admin' / 'admin'
- Configure settings:
  Server / Server manager / Server information / Edit properties / Server Name -> 'localhost'
  Server / Server settings / Offline messages -> 'Drop'
  Server / Server settings / Resource Policy -> 'Never kick'
- Create users:
  Username               Password
  auction-item-54321  -> auction
  auction-item-65432  -> auction
  sniper              -> sniper

Done.
