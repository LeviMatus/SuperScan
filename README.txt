Levi Matus
Dahn Balan
Nic Mingo

Due to the seperated classes, for ease of marking purposes we've precompiled all source code in the superscan package.
This should allow the compilation of the main class as specified by the assignment guidelines without extra legwork.
If you want to verify the integrity of our build, you can try to following steps below.

To compile only the runner, execute:
javac Scanner.java

To fully compile all source code, including the runnable, execute:
javac -d . dfa/*.java enums/*.java states/*.java transitions/*.java utils/*.java *.java

The runnable is in the default package, so you can simply execute:
java Scanner <filename>
to run the tokenizer.

