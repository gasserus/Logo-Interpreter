Logo-Interpreter
================

A LOGO interpreter with a probably nice UI.

At the moment the programm supports following commands:

| Command            | Description                                                                |
| ------------------ | -------------------------------------------------------------------------- | 
| forward x    		   | Turtle moves x forward                                                     |
| backward x   			 | Turtle moves x backwards                                                   |
| right x 		  		 | Turtle rotates x degrees right                                             |
| left x   			  	 | Turtle rotates x degrees left                                              |
| reset    				   | Turtle returns to Center                                                   |
| clear      				 | Previous painted Lines will be deleted                                     |
| penup      				 | Turtle is now drawing a line when movin (standard)                         |
| pendown    				 | Turtle is not drawing when moving                                          |
| setcolor x   			 | changes color of the Turtle ( 0 <= x <= 3 )                                |
| repeat x<br>[<br>"commands"<br>]           | repeats the action("commands") x times ( new Line before bracket )         |
| let var x 		     | set variable (var) to x (variable name is free as long it's not a number  )|
| increment var x: 	 | adds x to variable                                                         |
| decrement var x: 	 | remove x from variable                                                     |
| ;COMMENT           | e.g. forward 10 ;this moves the turtle 10px forward                        |
