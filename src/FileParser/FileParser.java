package FileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
    private String mFilename;
    private BufferedReader mInputStream;
    private char[] mStringBuffer;
    private int mBufferPosition;
    private boolean mBufferEmpty;

    public FileParser(String argFilename) {
		mInputStream = null;
		mStringBuffer = null;
		mBufferPosition = 0;
		mBufferEmpty = true;

		try {
			FileReader fileInputStream = new FileReader(argFilename);
			mInputStream = new BufferedReader(fileInputStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

    }

	public FileParser(File argFile) {
		mInputStream = null;
		mStringBuffer = null;
		mBufferPosition = 0;
		mBufferEmpty = true;

		try {
			FileReader fileInputStream = new FileReader(argFile);
			mInputStream = new BufferedReader(fileInputStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

    public boolean isReady() {
		if(mInputStream == null)
			return false;

		return true;
    }

    public FuncObj getStatement() {
		try {
			StringBuffer mStatementBuffer = new StringBuffer();
			boolean quotes = false;

			while(true) {
				if(mBufferEmpty) {
					 String tempString= mInputStream.readLine();

					 if(tempString == null)
					 return null;

					 mStringBuffer = tempString.toCharArray();
					 mBufferEmpty = false;
				}

				boolean endComment = false;
				int commentPos = 0;
				for(int i = mBufferPosition; i < mStringBuffer.length; i++) {
					switch(mStringBuffer[i]) {
					case '\"':
						if(quotes) {
							if(mStringBuffer[i-1] == '\\') {
								quotes = false;
							}
						}
						quotes = !quotes;
					break;
					case ';':
						if(!quotes) {
							mStatementBuffer.append(mStringBuffer, mBufferPosition, (i+1)-mBufferPosition);
							if(!(i+1 < mStringBuffer.length)) {
								mBufferPosition = 0;
								mBufferEmpty = true;
							} else {
								mBufferPosition = i+1;
							}

							return new FuncObj(mStatementBuffer.toString());
						}
					break;
					case '/':
						if(!quotes) {
							if(i+1 < mStringBuffer.length) {
								if(mStringBuffer[i+1] == '/') {
									endComment = true;
									commentPos = i;
									i = mStringBuffer.length-1;
								}
							}
						}
					break;
					}
				}

				if(endComment) {
					if(commentPos-1 > 1) {
						mStatementBuffer.append(mStringBuffer, mBufferPosition, (commentPos-1)-mBufferPosition);
						System.out.println(mStatementBuffer.toString());
					}
				} else {
					mStatementBuffer.append(mStringBuffer, mBufferPosition, mStringBuffer.length-mBufferPosition);
				}
				mBufferEmpty = true;
				mBufferPosition = 0;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
    }

		public static String getRelativePath(String targetFilePath, String startingFilePath) {
		if(targetFilePath != null && startingFilePath != null && !targetFilePath.isEmpty() && !startingFilePath.isEmpty()) {
			StringBuilder builder = new StringBuilder();

			char[] target = targetFilePath.toCharArray();
			char[] start = startingFilePath.toCharArray();

			// check for path absolution of both Linux or Windows (windows also uses \\\\ for absolute paths....)
			if((target[0] == '/' && start[0] == '/') || (target[1] == ':' && start[1] == ':')) {
				boolean done = false;
				int i = 0;
				int lastDirectoryPos = 0;
				boolean targetDirectoryStop = false, startDirectoryStop = false; // if the last character processed was before a new directory
				while(!done) {
					// iterate through the strings
					if(i < target.length) {
						if(i < start.length) {
							// check how far the strings match. save the pos of directory marker
							if(target[i] == start[i]) {
								if(target[i] == File.separatorChar) {
									lastDirectoryPos = i;
									targetDirectoryStop = startDirectoryStop = false;
								}

								if((i+1) < target.length || (i+1) < start.length) {
									if(target[i+1] == File.separatorChar)
										targetDirectoryStop = true;

									if(start[i+1] == File.separatorChar) {
										startDirectoryStop = true;
									}
								}
							} else {
								for(int j = lastDirectoryPos; j < start.length; j++) {
									if(start[j] == File.separatorChar) {
										builder.append(".." + File.separatorChar);
									}
								}
								builder.append(target, lastDirectoryPos+1, (target.length)-(lastDirectoryPos+1));
								done = true;
							}
						} else {
							if(targetDirectoryStop) {
								if(i+2 < target.length) {
									builder.append(target, i+2, (i+2)-(target.length));
									done = true;
								} else {
									System.out.println("improper target string");
									return null;
								}
							} else {
								System.out.println("There is something strange... in the neighbourhood");
							}
						}
					} else {
						 if(startDirectoryStop) {
							if(i+2 < start.length) {
								for(int j = lastDirectoryPos; j < start.length; j++) {
									if(start[j] == File.separatorChar) {
										builder.append(".." + File.separatorChar);
									}
								}
								// delete the last separator
								builder.deleteCharAt(builder.length()-1);
								done = true;
							} else {
								System.out.println("improper start string");
								return null;
							}
						} else {
							 return null;
						}

					}
					i++;
				}
				return builder.toString();
			} else {
				System.out.println("The paths are not absolute");
				return null;
			}
		} else {
			System.out.println("Paths are either null or empty");
			return null;
		}
	}

    public String getLine() {
		try {
			return mInputStream.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
    }
}
