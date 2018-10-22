package org.myorg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings("unused")
public class Combine extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text word, Iterable<Text> files, Context context) throws IOException, InterruptedException {
    	String endlist = "";
    	int i = 0;
    	String tmp = new String();
    	List<String> filelist = new ArrayList<String>();
    	List<Integer> filecounterlist = new ArrayList<Integer>();
    	for(Text file: files)
    	{
    		tmp = file.toString();
    		if(!filelist.contains(tmp)){
    			filelist.add(tmp);
    			filecounterlist.add(filelist.indexOf(tmp), 1);
    		}
    		else{
    			 i = filelist.indexOf(tmp);
    			filecounterlist.set(i, filecounterlist.get(i)+1);
    		}
    	}
    	
    	for(String filerec : filelist){
    		endlist = new StringBuilder().append(endlist).append(filerec).append(" #").append(Integer.toString(filecounterlist.get(filelist.indexOf(filerec)))).append("  ").toString();
    	}
    	context.write(new Text(word), new Text(endlist));
    }
}