Logo-Interpreter
================

A LOGO interpreter with a probably nice UI.

At the moment the programm supports following commands:

| Command            | Description                                                                |
| --------------------------------------------------------------------------- | ----------------------------------------------- | 
| forward x    		               | Turtle moves x forward                                                     |
| backward x   			 | Turtle moves x backwards                                                   |
| right x 		                                		 | Turtle rotates x degrees right                                             |
| left x   			  	 | Turtle rotates x degrees left                                              |
| reset    				   | Turtle returns to Center                                                   |
| clear      				 | Previous painted Lines will be deleted                                     |
| penup      				 | Turtle is now drawing a line when movin (standard)                         |
| pendown    				 | Turtle is not drawing when moving                                          |
| setcolor x   			 | changes color of the Turtle ( 0 <= x <= 3 )                                |
| repeat x<br>[<br>"commands"<br>]           | repeats the action("commands") x times ( new Line before bracket )         |
| function name parameter1 parameter2 ...<br>[<br>"commands"<br>]           | function name parameter1 parameter2 ...<br>defines a block of commands (parameters have to be names, no numbers)<br>parameters are optional |
| call functionName parameter1 parameter2 ...| call functionName parameter1 parameter2 ...<br>calls a function by name<br> parameters are oprional but the call amount of parameters should be as long as the amount of parameters of the defined function block|
| let var x 		     | set variable (var) to x (variable name is free as long it's not a number  )|
| increment var x: 	 | adds x to variable                                                         |
| decrement var x: 	 | remove x from variable                                                     |
| ;COMMENT           | e.g. forward 10 ;this moves the turtle 10px forward                        |
