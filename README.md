# Informatics 102
*Exercises in Programming Style*

## Task
Conform to the specified style. See the style's constraints in the text.  
Efficiency (running time) is not (really) considered, for some reason.  
Implementations in Java and Ruby

# Styles
## Freestyle
Do it the way you like.

## 4 Cookbook
Shared state that's processed by a series of functions.
	
## 5 Pipeline
Functional-style, return output = next input. No shared state.

## 6 Code Golf
Short and sweet (often not explicit).
	
## 7 Infinite Mirror
Recursion. Induction: Base Case, N + 1.  
Concept: Tail recursion optimization/elimination.  
Tail call: Subroutine call that's the final action in a procedure.  
If the activation record (state) doesn't need to be preserved (pop followed by push)  
the compiler optimizes to reuse the activation record.  
Only one activation record is used, saves space + time.  
Stack frame doesn't grow (and overflow).  
May or may not be supported by the programming environment.  
*Python imterpreter doesn't support tail call elimination.
	
## 8 Kick Forward
Continuous-passing. Often used with anonymous function (lambda expressions).  
The next function to be called is passed as an argument to the called function.  
Popular due to asynchronous programming (e.g. JavaScript), non-blocking calls.

## 9 The One
Use an identity monad.  
The constructor wraps the monad around a value.  
Bind function feeds value to next function.  
Returns self/instance that wraps the new result.  
Ends up being like left-to-right pipeline.  
@_value modified instead of return val.

<dl>	
	<dt>Monad</dt>
	<dd>
		Encapsulates computations as a sequence of steps.
		<ul>
			<li>Wrap value within monad. Wrap == 'return' (Haskell)</li>
			<li>Bind applies function on wrapped value and returns a monad</li>
			<li>Unwrap/print/evaluate</li>
		</ul>
	</dd>
</dl>

## 12 Closed Maps
"Classes" as Maps, like in JavaScript.

## 15 Bulletin Board (Publish/Subscribe)
Process via message passing with Pub/Sub model. No direct calls.  
Publish and subscribe to events posted to the "bulletin board."  
Events are pairs of event types and handlers.  
Event type 	= The name.  
Handlers 	= The method to be called by the subscribers.  
Bulletin board is the event manager, it's the infrastructure that  
performs event management and distribution.

## 16 Introspective
Use information about self. (local stack vars)
	
## 19 Plugins
Pluggable/modular capabilities.  
Call subroutines available in compiled binaries.  
Dynamic loading of packages.  
Use of configuration files to specify code to be  
loaded at runtime.

## 23 Declared Intentions
Type enforcement.  
Procedures/functions declare the types of the parameters.  
Type errors raised if caller sends arguments with incompatible types.  
(Add this to dynamically-typed languages (Python, Ruby))  
*This is standard in Java for arguments and return values.
	
## 24 Quarantine
No side effects of any kind, including IO.  
IO actions contained separately from pure functions.  
IO sequences called from main.

## 25 Persistent Tables
Data exists outside of execution program and often used by  
many different programs. Data is stored so it's easier to explore.  
Modeled as domains or types of data. Relations exist between  
application data and domains identified.  
Solve the problem by querying over data.  
(Use SQLite)

## 27 Lazy Rivers
Data comes in streams/chunks instead of whole.  
Functions transform data stream to another kind of data stream. 
Data is processed on need basis.  
(Generators/Enumerators (Iterators + Bookkeeping, for partial results))

## 28 Actors
Large problem decomposed into things.  
Things have message queues to receive data.  
Each thing has its own indepedent execution thread.  

## 29 Dataspaces
One or more units execute concurrently.  
One or more data spaces where the concurrent  
units store/retrieve data.  
No direct data exchange between concurrent units.  
Data exchange only occurs using the one or more data spaces.

## 30 Map Reduce
Divide input into pieces.  
Apply Map function to each piece, potentially in parallel.  
Reduce function takes result from workers and recombines  
into coherant output (merge).  
Programming model rooted in LISP, APL

## 31 Double Map Reduce
Like MapReduce  
Purpose: Do the job in parallel by grouping.  
Often data structures can be transformed for parallel processing.  
Data grouped under some criteria for processing.
Model: Google MapReduce, Hadoop

## 32 Model-View-Controller
Model - Data models and rules that apply to the data.  
View - Presents visual representation of model. Formats data for user.  
Controller - Communication link between Model and View. Receive input,  
change the model, decide action to be performed (present view).  The coordinator.  
No overlap of responsibilities

# Demonstration Program
Word frequency counter with stop words  
The program takes 1 argument, the text to scan.

##
Run
	>> [Program] pride-and-prejudice.txt

## Java
	>> javac Program.java
	>> java  Program.class pride-and-prejudice.txt

## Ruby
	>> ruby Program.rb pride-and-prejudice.txt


## Input
A text file containing the text to be scanned.  
In our case, the text of the book Pride and Prejudice  
provided in pride-and-prejudice.txt.

## Output
List of the top 25 most frequently occuring words in descending order.

## Format
	[word 1] - [count 1]
	[word 2] - [count 2]
	[word 3] - [count 3]
	[word 4] - [count 4]
	...

## Correct output for pride-and-prejudice.txt
	mr - 786
	elizabeth - 635
	very - 488
	darcy - 418
	such - 395
	mrs - 343
	much - 329
	more - 327
	bennet - 323
	bingley - 306
	jane - 295
	miss - 283
	one - 275
	know - 239
	before - 229
	herself - 227
	though - 226
	well - 224
	never - 220
	sister - 218
	soon - 216
	think - 211
	now - 209
	time - 203
	good - 201

## List of Stop Words
### Location
    ../stop_words.txt  (in the parent directory)

### stop_words.txt
    a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,
	be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,
	every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,
	in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,
	neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,
	says,she,should,since,so,some,than,that,the,their,them,then,there,these,
	they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,
	who,whom,why,will,with,would,yet,you,your,

