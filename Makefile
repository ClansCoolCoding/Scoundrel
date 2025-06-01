all: clean compile run

clean:
	@echo cleaning all the files
	wsl rm -rf out
	@echo finished cleaning

compile: out
	@echo compiling src/Card
	javac -d out src/Card/*.java
	@echo compiling src/GameManagement
	javac -d out src/GameManagement/*.java
	@echo compiling src
	javac -d out src/*.java
	@echo finished compiling

out:
	wsl mkdir "out"

run:
	java -cp out src.Main