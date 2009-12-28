You have successfully created a plugin using the Confluence plugin archetype. What to do now:

1. CUSTOMISE THE PLUGIN

- Generate project files for your IDE
  - If you use Eclipse, run 'mvn eclipse:eclipse' to generate an Eclipse project file.
  - If you use IDEA, run 'mvn idea:idea' to generate an IDEA project file.
- Edit pom.xml. Add information about your project, its developers and your organisation. Check the version of
  Confluence in the dependencies section is correct.
- Edit the plugin descriptor, src/main/resources/atlassian-plugin.xml. Add or modify plugin modules in your project.
- Edit the plugin code in src/main/java/ or the unit tests in src/test/java/.

More documentation on Atlassian plugins is available here:

http://confluence.atlassian.com/display/DEVNET/How+to+Build+an+Atlassian+Plugin


2. BUILD THE PLUGIN

Building with your plugin with Maven is really easy:

- Run 'mvn compile' to compile the plugin.
- Run 'mvn test' to run the unit tests.
- Run 'mvn package' to produce the JAR.

It's possible to build and test the plugin with different versions of Confluence. Such control is available by defining certain POM properties:

- atlassian.product.version - This is the version of Confluence you wish the test the plugin with.
- atlassian.product.data.version - To support launching of the version of Confluence specified above, you may need to set a different product data version. You can find versions of it available for use at http://svn.atlassian.com/svn/public/atlassian/confluence/plugins/confluence-plugin-test-resources/tags/.
- atlassian.product.test-lib.version - Plugins can build functional tests using a custom-built functional test library. A functional test library is a library that supports manipulation of Confluence for functional tests. For instance, you can create, edit and delete pages using the functional test library. You can find versions of this library available for use at http://svn.atlassian.com/svn/public/contrib/confluence/confluence-plugin-func-test/tags/.

Please remove this file before releasing your plugin.


** NOTE ON RESOURCE FILTERING **

The default pom has 'resource filtering' enabled, which means files in the src/main/resources directory will have
variables in the form ${var} replaced during the build process. For example, the default atlassian-plugin.xml includes
${project.artifactId}, which is replaced with the artifactId taken from the POM when building the plugin.

More information on resource filtering is available in the Maven documentation:

http://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html

