# FileCompression
This program basically implements LZW algorithm for compressing the file.

Creating a StringZipOutputStream class, similar to GZipOutputStream. You can not use java classes for the compression. A skeleton of the class can be found here:
 1
 2
 3      import java.io.OutputStream;
 4
 5      public class StringZipOutputStream      {
 6
 7              // Creates a new output stream with a default buffer size.
 8              public StringZipOutputStream(OutputStream out)  {
 9              }
10              // Writes aStrign compressed output stream. This method will block until all data is written.
11              public void write(String aString) {
12              }
13              // Writes remaining data to the output stream and closes the underlying stream.
14              public void close() {
15              }
16      }

- You can assume the input will consist of a file. 
- The file will only have characters/strings. 
- Each line will have one or more words. 
- Each word is less than 100 characters long. 
- Keep in mind the data file could be extremly large.
