/*
 * StringZipInputStream.java
 *
 * Version: 1.1: StringZipInputStream.java,v 1.1 2015/10/12 22:39:23
 *
 * Revisions: 
 * 		Revision 1.1 Kapil 2015/10/12 23:37:11
 *      Initial version
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This program basically implements LZW algorithm for uncompressing the file.
 * It builds its own dictionary as it encounters strings, similar logic as
 * while compressing file. This program uncompresses file line by line and
 * returns uncompressed line.
 *
 * @author      Kapil Dole
 */
public class StringZipInputStream	{
	
	private InputStream reader;
	private BufferedReader bufRead;
	String[] dictionary = new String[1000000];
    int dictionaryLength;
	
    /**
 	 *	Creates a new input stream with a default buffer size.
     *
     *  @param        out              InputStream object.
     *
     *  @return       				   none.
     */
	public StringZipInputStream(InputStream out)	{
		this.reader = out;
		bufRead = new BufferedReader(new InputStreamReader(reader));
	}
	
    /**
 	 *	Reads data into a string. the method will block until some input can 
 	 *  be read; otherwise, no bytes are read and null is returned.
     *
     *  @param                         none.
     *
     *  @return       writeLine		   uncompressed line.
     */
	public String read() {
		
		String writeLine="";
		boolean foundWord;
		try {
			String line=bufRead.readLine();
			if(!(line==null)){
				String[] wordList = line.split(" ");
				//uncompress word by word.
				for(String eachWord : wordList){
					
					/* If the given word is all digits, then we are replacing
					 * it with dictionary string, else if it finds string in 
					 * #digit format, it will remove # from it, otherwise
					 * it will print string as it is.
					 * */
					if(eachWord.matches("#\\d+")){
						writeLine=writeLine+eachWord.replaceAll("#", "")+" ";
					}
					else if(eachWord.matches("\\d+")){
						writeLine=writeLine+dictionary[Integer.parseInt(eachWord)]
								+" ";
					}
					else{
						writeLine=writeLine+eachWord+" ";
						for(int charCounter=1;charCounter<=eachWord.length();
								charCounter++){
							foundWord = false;
							for(int dicCounter =0 ; dicCounter<dictionaryLength;
									dicCounter++){
								if(eachWord.substring(0,charCounter).
										equals(dictionary[dicCounter])){
									foundWord = true;
									break;
								}
							}
							if(!foundWord){
							dictionary[dictionaryLength]=eachWord.
									substring(0,charCounter);
							dictionaryLength++;
							
							}				
						}
					}
				}
				writeLine=writeLine.trim()+"\n";				
			}
			else{
				writeLine=null;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}				
		return writeLine;
	}
	
    /**
 	 *	Closes this input stream and releases any system resources associated
 	 *  with the stream.
     *
     *  @param                         none.
     *
     *  @return       				   none.
     */
	public void close() {
		try {
			reader.close();
			bufRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
} 