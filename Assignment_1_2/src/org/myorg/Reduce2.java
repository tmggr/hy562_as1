package org.myorg;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings("unused")
public class Reduce2 extends Reducer<Text, LongWritable, Text, Text> {
    //@Override 
    public void reduce(Text value, Iterable<Text> words, Context context) throws IOException, InterruptedException {
      for( Text word : words)
      {
    	  context.write(value, word);
      }
    }
}

