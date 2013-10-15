/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileParser;

/**
 *
 * @author kamil
 */
import java.util.Vector;
import java.util.StringTokenizer;

public class FuncObj {
    public FunctionType mFunction;
    public Vector<String> mArguments;

    public enum FunctionType {
		ERROR,
		ASSIGN,
		FUNCCALL,
		IFSTART,
    };

    public FuncObj(String argStatement) {
		mFunction = FunctionType.ERROR;
		mArguments = new Vector<String>();

		if(!argStatement.isEmpty()) {
			serializeStatement(this, argStatement);
		}
    }

	private void falsify() {
		mFunction = FunctionType.ERROR;
		String arg = mArguments.elementAt(0);
		mArguments.clear();
		mArguments.add(arg);
	}

	public boolean MatchFuncID(String match) {
		return mArguments.elementAt(0).equalsIgnoreCase(match);
	}

    private static void serializeStatement(FuncObj obj, String argStatement) {
		char[] buffer = argStatement.toCharArray();

		for(int i = 0; i < buffer.length; i++) {
			switch(buffer[i]) {
			/////////////////////
			// ASSIGN FUNCTION //
			/////////////////////
			case '=':
				// Get name of variable assigned
				boolean wordCaught = false;
				StringBuffer mArgumentBuffer = new StringBuffer();
				for(int j = 0; j < i; j++) {
					if(wordCaught) {
						if(Character.isLetterOrDigit(buffer[j])) {
							mArgumentBuffer.append(buffer[j]);
						} else {
						break;
						}
					} else {
						if(Character.isLetter(buffer[j])) {
							wordCaught = true;
							mArgumentBuffer.append(buffer[j]);
						}
					}
				}
				if(!wordCaught)
					break;
				else
					obj.mFunction = FunctionType.ASSIGN;

				// Check for array assignment
				char[] ArgBuffer = mArgumentBuffer.toString().toCharArray();
				StringBuffer arrayDimensionBuffer = new StringBuffer();
				int arrayStartPos = 0;;
				boolean arrayStarted = false;
				for(int j = 0; j < i; j++ ) {
					switch(buffer[j]) {
					case '[':
						if(!arrayStarted) {
							arrayStarted = true;
							arrayStartPos = j;
						}
					break;
					case ']':
						if(arrayStarted) {
							for(int k = arrayStartPos+1; k < j; k++) {
								arrayDimensionBuffer.append(buffer[k]);
							}
							arrayDimensionBuffer.append('x');
							arrayStarted = false;
						}
					break;
					}
				} // variable name
				obj.mArguments.add(trimWhiteSpace(mArgumentBuffer.toString()));

				if(arrayDimensionBuffer.toString().isEmpty()) { // not an array
					boolean quotes = false;
					for(int j = i+1; j < buffer.length; j++) {
						switch(buffer[j]) {
						case '\"':
							if(quotes) {
								if(buffer[j-1] == '\\') {
									quotes = false;
								}
							}
							quotes = !quotes;
						break;
						case ';':
							if(!quotes) {
								arrayDimensionBuffer.append(buffer, i+1, j-(i+1));
								obj.mArguments.add(trimWhiteSpace(arrayDimensionBuffer.toString()));
								return;
							}
						break;
						}
					}
				} else {
					// handle arrays here
					obj.mArguments.add(arrayDimensionBuffer.toString());
					int[] dimensions = retrieveIntegers(obj.mArguments.elementAt(1));
					int[] dimensionCounters = new int[dimensions.length];
					// iterator is the current dimension:
					// (e.g. In a 3 dimensional array x[][][], iterator = 1 means the loop is operating on the second dimension
					int iterator = 0, j = i+1, argStartPos = 0;

					boolean done = false, quotes = false;

					while(!done) {
						switch(buffer[j]) {
						case '{':
							// Check only if quotes are in effect
							if(!quotes) {
								// if it's not the last dimension
								if(!((iterator+1) > dimensions.length)) {
									// Check if the current dimensions is full
									if((dimensionCounters[iterator]+1) == dimensions[iterator]) {
										obj.falsify();
										return;
									}
									iterator++;


									if(iterator == dimensions.length) {
										argStartPos = j+1;
									}
								} else {
									obj.falsify();
									return;
								}

							}
						break;
						case '}':
							if(!quotes) {
								// Check if there is a dimension below
								if(!((iterator-1) < 0)) {
									// Check if this is the last item in the current dimension
									if(dimensionCounters[iterator-1]+1 == dimensions[iterator-1]) {
										// make sure there is input
										if(iterator == dimensions.length) {
											if(j-1 > argStartPos) {
												// Add input as argument
												obj.mArguments.add(trimWhiteSpace(new String(buffer, argStartPos, (j)-argStartPos)));
												argStartPos = j+1;
											} else {
												obj.falsify();
												return;
											}
										}
									} else {
										obj.falsify();
										return;
									}

									dimensionCounters[iterator-1] = 0;

									iterator--;
								} else {
									obj.falsify();
									return;
								}

							}
						break;
						case '\"':
							if(iterator == dimensions.length) {
								if(quotes) {
									if(buffer[j-1] == '\\') {
										quotes = false;
									}
								}
								quotes = !quotes;
							} else {
								obj.falsify();
								return;
							}
						break;
						case ',':
							if(!quotes) {
								// Because '}' checks for last element, make sure ',' separates only elements before the last one
								if((dimensionCounters[iterator-1])+1 != (dimensions[iterator-1])) {
									dimensionCounters[iterator-1]++;

									// if it is in the last dimension
									if(iterator == dimensions.length) {
										if(j-1 > argStartPos) {
											// Add input as argument
											obj.mArguments.add(trimWhiteSpace(new String(buffer, argStartPos, (j)-argStartPos)));
											argStartPos = j+1;
										} else {
											obj.falsify();
											return;
										}
									}
								} else {
									obj.falsify();
									return;
								}
							}
						break;
						case ';':
							if(!quotes) {
								if(iterator == 0) {
									return;
								} else {
									obj.falsify();
									return;
								}
							}
						break;
						}
						j++;
					}
				}
			return;
			//////////////
			// FUNCCALL //
			//////////////
			case '(':
				/////////////////////
				// Get function ID //
				/////////////////////
				for(int j = 0; j < i; j++) {
					if(!Character.isWhitespace(buffer[j])) {
						obj.mArguments.add(String.valueOf(buffer, j, i-j));
						obj.mFunction = FunctionType.FUNCCALL;
						break;
					}
				}

				////////////////////////
				// Retrieve arguments //
				////////////////////////
				boolean quotes = false;
				int argStartPos = i+1;
				boolean argPresent = false; // Check if there is an actual argument after a comma
				boolean funcEnd = false;
				for(int j = i; j < buffer.length; j++) {
					if(!Character.isWhitespace(buffer[j]) && buffer[j] != '\"' && buffer[j] != ')') {
						argPresent = true;
					}

					switch(buffer[j]) {
					case '\"':
						if(quotes) {
							if(buffer[j-1] == '\\') {
								quotes = false;
							}
						}
						quotes = !quotes;
					break;
					case ',':
						if(!quotes) {
							if(argPresent) {
								obj.mArguments.add(trimWhiteSpace(String.valueOf(buffer, argStartPos, j-argStartPos)));
								argPresent = false;
								argStartPos = j+1;

							} else {
								System.out.println("Empty argument position");
								obj.falsify();
								return;
							}
						}
					break;
					case ')':
						if(!quotes) {
							if(argPresent) {
								obj.mArguments.add(trimWhiteSpace(String.valueOf(buffer, argStartPos, (j)-argStartPos)));
								funcEnd = true;
							}
						}
					break;
					case ';':
						if(!quotes) {
							if(funcEnd) {
								return;
							} else {
								obj.falsify();
							}
						}
					break;
					}
				}
			return;
			}
		}
    }

	public static String retrieveString(String argString) {
		String someString = null;
		if(!argString.isEmpty()) {
			char[] tempBuffer = argString.toCharArray();
			boolean quotes = false;
			int stringStart = 0;
			for(int i = 0; i < tempBuffer.length; i++) {
				if(tempBuffer[i] == '\"') {
					if(quotes) {
						if(tempBuffer[i-1] == '\\') {
							quotes = false;
						}
					} else {
						stringStart = i+1;
					}
					quotes = !quotes;

					if(!quotes) {
						someString = new String(tempBuffer, stringStart, (i)-stringStart);
					}
				}
			}
		}

		return someString;
	}

	public static int[] retrieveIntegers(String argString) {
		int[] integers = null;

		if(!argString.isEmpty()) {
			StringTokenizer tokenizer = new StringTokenizer(argString, "x");
			integers = new int[tokenizer.countTokens()];

			for(int i = 0; i < integers.length; i++) {
				if(tokenizer.hasMoreTokens()) {
					integers[i] = Integer.parseInt(tokenizer.nextToken());
				}
			}
		}

		return integers;
	}

	public static float[] retrieveFloats(String argString) {
		float[] floats = null;

		if(!argString.isEmpty()) {
			StringTokenizer tokenizer = new StringTokenizer(argString, "x");
			floats = new float[tokenizer.countTokens()];

			for(int i = 0; i < floats.length; i++) {
				if(tokenizer.hasMoreTokens()) {
					floats[i] = Float.parseFloat(tokenizer.nextToken());
				}
			}
		}

		return floats;

	}

	public static String trimWhiteSpace(String argStr) {
		char[] arg = argStr.toCharArray();
		int argStrt = 0;
		int argEnd = arg.length-1;
		for(int j = argStrt; j < arg.length; j++) {
			if(!Character.isWhitespace(arg[j])) {
				argStrt = j;
				break;
			}
		}
		for(int j = argEnd; j > argStrt; j--) {
			if(!Character.isWhitespace(arg[j])) {
				argEnd = j;
				break;
			}
		}

		return String.valueOf(arg, argStrt, argEnd+1-argStrt);
	}
}
