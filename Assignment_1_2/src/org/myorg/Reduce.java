package org.myorg;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings("unused")
public class Reduce extends Reducer<Text, LongWritable, Text, LongWritable> {
    @Override public void reduce(Text word, Iterable<LongWritable> counts, Context context) throws IOException, InterruptedException {
      int sum = 0;
      for (LongWritable count : counts) {
       sum += count.get();
      }
      context.write(word, new LongWritable(sum));
    }
}

