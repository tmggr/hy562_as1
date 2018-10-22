package org.myorg;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import java.util.regex.Matcher;

import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
 
@SuppressWarnings("unused")
public class WordCount extends Configured implements Tool {
	
	private static final String OUTPUT_PATH = "intermediate_output";
	private static final Logger LOG = Logger.getLogger(WordCount.class);
	public static void main(String[] args) throws Exception {
	     int res = ToolRunner.run(new WordCount(), args);
	     System.exit(res);
	}
   
	public int run(String[] args) throws Exception {
		final long startTime = System.currentTimeMillis();
		Job job1 = Job.getInstance(getConf(), "wordCount");
		job1.setJarByClass(WordCount.class);
		job1.setMapperClass(Map.class);
		job1.setReducerClass(Reduce.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(LongWritable.class);
		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(OUTPUT_PATH));
		if (!job1.waitForCompletion(true)) {
		  System.exit(1);
		}
		getConf().set("mapred.textoutputformat.separator", ",");
		Job job2 = Job.getInstance(getConf(),"frequency");
		job2.setJarByClass(WordCount.class);
		job2.setMapperClass(Map2.class);
		job2.setReducerClass(Reduce2.class);		
		job2.setOutputKeyClass(LongWritable.class);
		job2.setSortComparatorClass(LongWritable.DecreasingComparator.class);
		job2.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job2, new Path(OUTPUT_PATH));
		FileOutputFormat.setOutputPath(job2, new Path(args[1]));
		if (!job2.waitForCompletion(true)) {
		  System.exit(1);
		}
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) );
		return 1;
	}
}

