# Detect OS
ifeq ($(OS),Windows_NT)
    RM = wsl rm -rf
    MKDIR = wsl mkdir
    TIME = wsl time
else
    RM = rm -rf
    MKDIR = mkdir -p
    TIME = time
endif

all: clean compile run

clean:
	@echo cleaning all the files
	$(RM) out
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
	$(MKDIR) out

run:
	java -cp out src.Main
