package org.myorg;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings("unused")
public class Reduce extends Reducer<Text, Text, Text, Text> {
    //@Override 
	private int counter = 1;
	private String filesstring="";
    public void reduce(Text word, Iterable<Text> files, Context context) throws IOException, InterruptedException {
    	String concated="";
		for(Text file : files){
			concated = new StringBuilder().append(concated).append(file.toString()).toString(); 
		}
    	String counterWord = Integer.toString(counter) + " " + word.toString();
		counter++;
		context.write(new Text(counterWord), new Text(concated));
    }
    	
}

