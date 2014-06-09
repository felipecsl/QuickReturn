mvn install:install-file \
-DgroupId=com.felipecsl \
-DartifactId=quickreturn \
-Dversion=$1 \
-DgeneratePom=true \
-Dpackaging=aar \
-Dfile=library/build/outputs/aar/library.aar \
-DlocalRepositoryPath=/Users/felipecsl/Data/Projects/m2repository/

mv /Users/felipecsl/Data/Projects/m2repository/com/felipecsl/quickreturn/$1/maven-metadata-local.xml \
/Users/felipecsl/Data/Projects/m2repository/com/felipecsl/quickreturn/$1/maven-metadata.xml

mv /Users/felipecsl/Data/Projects/m2repository/com/felipecsl/quickreturn/maven-metadata-local.xml \
/Users/felipecsl/Data/Projects/m2repository/com/felipecsl/quickreturn/maven-metadata.xml