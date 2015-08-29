Digestion
=========

2D Zombie Platformer

A Thing Thing Arena styled 2D platformer when the player has to survive levels of incoming zombies by gunning them down.

Required libraries:

**Jython** - Jar of the library can be downloaded here: http://mirrors.ibiblio.org/maven2/org/python/jython/2.7.0/

Currently required but is likely to be soon removed as a dependency. It has been used for scripting levels and entities. Both of those things are likely to be switched to some kind of json format since it's easier to work with.

**simplejson** - Jar of the library can be downloaded here: https://code.google.com/p/json-simple/downloads/detail?name=json-simple-1.1.1.jar&can=2&q=

All the script files are likely to be replaced with json files that simply describe the entity's parameters whereas the entities themselves are defined in code (it's easier to manage that way).

**JBox2D 2.2.1.1** - Jar of the library (which can be hard to find/compile) can be downloaded here: http://grepcode.com/snapshot/repo1.maven.org/maven2/org.jbox2d/jbox2d-library/2.2.1.1

Just make sure you include the Jars for all libraries in the build path when the code is compiled. If you're using Eclipse, right-click project -> Build Path -> Add External Libraries and select all the appropriate jars.
