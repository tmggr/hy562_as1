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
	public enum BOOK_COUNTER { pg100, pg1120, pg1513, pg2253, pg31100, pg3200 };
	
	private static final Logger LOG = Logger.getLogger(WordCount.class);
	public static void main(String[] args) throws Exception {
	     int res = ToolRunner.run(new WordCount(), args);
	     System.exit(res);
	}
   
	public int run(String[] args) throws Exception {
	
		Job job1 = Job.getInstance(getConf(), "inv_index_with_counter");
		job1.addCacheFile(new Path(args[2]).toUri());
		job1.setJarByClass(WordCount.class);
		job1.setMapperClass(Map.class);
		job1.setCombinerClass(Combine.class);
		job1.setReducerClass(Reduce.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(args[1]));
		if (!job1.waitForCompletion(true)) {
		  System.exit(1);
		}
		
		return 1;
	}
}

