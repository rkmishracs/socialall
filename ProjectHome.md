Purely Java project to connect to various social networks like Twitter, Facebook, Myspace, LinkedIn, Google Buzz, etc.

I am hoping this project will grow over time, the next target is the to get better testing and build a "universal wall" to collect all activity across social networks.

This is under MIT license so feel free to do whatever with the code base.

You would like to see a sample of what is done as of now, please checkout
http://www.shoutall.com . This website will have all major releases deployed and will server as a working proof of concept.

Getting Started:

1. Checkout source code.

2. Copy maven\_repo contents in your .m2 folder ( I could not find maven repo for these libraries, please email me if you find it. )

3. Install MySQL

4. Edit pom.xml and put your db credentials

5. Edit keys.properties and put your social network public and private keys

6. mvn -Dmaven.test.skip=true jetty:run-war

Thats it !