package org.myorg;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.myorg.WordCount.BOOK_COUNTER;

@SuppressWarnings("unused")
public class Reduce extends Reducer<Text, Text, Text, Text> {
    //@Override 
	private int counter = 1;
    public void reduce(Text word, Iterable<Text> files, Context context) throws IOException, InterruptedException {
    	String filelist = "";
    	for(Text file: files)
    	{
    		if(!filelist.contains(file.toString()))
    		{
    			if(file.toString().contains("pg100.txt"))
                	context.getCounter(BOOK_COUNTER.pg100).increment(1);
        		else if(file.toString().contains("pg1120.txt"))
                	context.getCounter(BOOK_COUNTER.pg1120).increment(1);
        		else if(file.toString().contains("pg1513.txt"))
            		context.getCounter(BOOK_COUNTER.pg1513).increment(1);
        		else if(file.toString().contains("pg2253.txt"))
        			context.getCounter(BOOK_COUNTER.pg2253).increment(1);
        		else if(file.toString().contains("pg31100.txt"))
    				context.getCounter(BOOK_COUNTER.pg31100).increment(1);
        		else if(file.toString().contains("pg3200.txt"))
    				context.getCounter(BOOK_COUNTER.pg3200).increment(1);
    			filelist = new StringBuilder().append(filelist).append(file.toString()).append(" ").toString();
    		}
    	}
      String counterWord = Integer.toString(counter) + " " + word.toString();
      counter++;
      context.write(new Text(counterWord), new Text(filelist));
    }
}

