package org.myorg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class Map extends Mapper<LongWritable, Text, Text, Text> {
    
    private static final Pattern WORD_CHECK = Pattern.compile("[a-zA-Z]+(?:'[a-zA-Z]+)?");
   
    public void map(LongWritable offset, Text lineText, Context context) throws IOException, InterruptedException {
    	String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
    	String line = lineText.toString().toLowerCase();          	 	
	 	
		String stopline;
		
		Text currentWord  = new Text();
		Text file = new Text();
		URI[] localPaths = context.getCacheFiles();
		ArrayList<String> stopwordslist = new ArrayList<String>();
        
        BufferedReader br = new BufferedReader(new FileReader(new File(localPaths[0].getPath()).getName()));
        while ((stopline = br.readLine()) != null) {

            String[] wordFreqs = stopline.split(", ");

            stopwordslist.add(wordFreqs[1]);
        }
        br.close();
        String word = new String();
        Matcher matcher = WORD_CHECK.matcher(line);
        while (matcher.find()){
        	word = matcher.group(0);            
            
       	 	if(stopwordslist.contains(word))
       	 		continue;
            
            currentWord = new Text(word);
            file = new Text(fileName);            
            context.write(currentWord,file);
        }
    }
}