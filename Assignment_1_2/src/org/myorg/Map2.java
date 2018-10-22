package org.myorg;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
@SuppressWarnings("unused")

public class Map2 extends Mapper<LongWritable, Text, LongWritable, Text>{
	private static final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");
	
    public void map(LongWritable value, Text lineText, Context context) throws IOException, InterruptedException {
    	String line = lineText.toString();
    	String[] parts = line.split("\\s+");
    	Text currentWord = new Text(parts[0]);       	
    	Long number = Long.parseLong(parts[1]);
    	
    	if(number > 4000)
    	{   	
    		context.write(new LongWritable(number) ,currentWord);
    	}
	
    }	
}
