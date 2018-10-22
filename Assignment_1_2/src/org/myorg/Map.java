package org.myorg;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

@SuppressWarnings("unused")
public class Map extends Mapper<LongWritable, Text, Text, LongWritable> {
   	
    private final static LongWritable one = new LongWritable(1);
    private Text word = new Text();
    private static final Pattern WORD_CHECK = Pattern.compile("[a-zA-Z]+(?:'[a-zA-Z]+)?");

    private boolean caseSensitive = false;
    protected void setup(Mapper.Context context) throws IOException,InterruptedException{
 	      Configuration config = context.getConfiguration();
 	      this.caseSensitive = config.getBoolean("wordcount.case.sensitive", false);
    }    
    public void map(LongWritable offset, Text value, Context context) throws IOException, InterruptedException {
    	String line = value.toString();
        line = line.toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(line);

        while (tokenizer.hasMoreTokens()){
        	word.set(tokenizer.nextToken());
            context.write(word,one);
        }
    }
}