/*
 * StringZipOutputStream.java
 *
 * Version: 1.1: StringZipOutputStream.java,v 1.1 2015/10/12 20:38:23
 *
 * Revisions: 
 * 		Revision 1.1 Kapil 2015/10/12 22:31:11
 *      Initial version
 */

import java.io.IOException;
import java.io.OutputStream;

/**
 * This program basically implements LZW algorithm for compressing the file.
 * It builds its own dictionary as it encounters strings, same logic it 
 * implements while uncompressing file. This program compresses file line by line
 * and return compressed line.
 *
 * @author      Kapil Dole
 */

public class StringZipOutputStream	{

    private OutputStream writer;
    private String compressedString;
    String[] dictionary = new String[1000000];
    int dictionaryLength;
    
    /**
 	 *	Creates a new output stream with a default buffer size.
     *
     *  @param        out              OutputStream object.
     *
     *  @return       				   none.
     */
    public StringZipOutputStream(OutputStream out)	{
        this.writer = out;
    }
    
    /**
 	 *	Writes aStrign compressed output stream. This method will block until all
 	 *  data is written.
     *
     *  @param        aString          String to compress.
     *
     *  @return       				   none.
     */  
    public void write(String aString) throws IOException {
        compressedString = compress(aString);
        this.writer.write(compressedString.getBytes());
    }
    
    /**
     *  In this method, we are compressing file line by line, so it takes file 
     *  line as input compress the line using LZW compression algorithm and 
     *  then return compressed line.
     *
     *  @param        fileLine         uncompressed file line.
     *
     *  @return       writeLine        compressed file line.
     */
	public String compress(String fileLine){
		boolean found, foundWord;

					//getting all words in line in an String array.
					String[] wordList = fileLine.split(" ");
					String writeLine="";
					for(String eachWord : wordList){
						
						/* If the given word is all digits, then we are replacing
						 * it with #digit format, to differentiate encoded 
						 * String and the digit string, otherwise if the given
						 * word is not already in dictionary then it will add
						 * it to the dictionary.*/
						if(eachWord.matches("\\d+")){	
							writeLine=writeLine+
									eachWord.replaceAll("\\d+", "#$0")+" ";
						}
						else{
							found = false;
							//checking if word is in dictionary or not.
							for(int counter =0 ; counter<dictionaryLength;
									counter++){						
								if((eachWord.equals(dictionary[counter]))){
									found = true;
									writeLine=writeLine+String.valueOf(counter)+" ";
									break;
								}
							}
							//if word is not in dictionary then add it to dictionary.
							if(!found){
								writeLine=writeLine+eachWord+" ";
								for(int charCounter=1;charCounter<=eachWord.length();
										charCounter++){
									foundWord = false;
									for(int counter =0 ; counter<dictionaryLength;
											counter++){
										if(eachWord.substring(0,charCounter)
												.equals(dictionary[counter])){
											foundWord = true;
											break;
										}
									}
									if(!foundWord){
									dictionary[dictionaryLength]=eachWord
											.substring(0,charCounter);
									dictionaryLength++;
									}					
								}
							}
						}
					}
					return writeLine.trim()+"\n";			
	}
    
    /**
     *  Writes remaining data to the output stream and closes the underlying stream.
     *
     *  @param        	none.
     *
     *  @return         none.
     */
    public void close() throws IOException {
        writer.close();
    }
}