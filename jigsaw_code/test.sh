mkdir -p build
javac -encoding UTF-8 -d build -cp lib/jigsaw.jar src/solution/Solution.java
java -cp lib/jigsaw.jar:build judge.main

