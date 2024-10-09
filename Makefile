compil: GraphROEleve.class

GraphROEleve.class:src/graphro/*java
	javac --source-path ./src --class-path ./classes -d ./classes ./src/graphro/GraphROEleve.java

run: classes/graphro/GraphROEleve.class
	java -classpath ./classes/ graphro.GraphROEleve
