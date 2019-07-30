package hw3_205814890;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Classify {

	public static Map<String,Integer> countthewords(ArrayList<String> data , ArrayList<String> stoplist)
	{
		Map<String,Integer> countwords = new HashMap<String,Integer>();
		for(String sentence: data)
		{
			String [] towords = sentence.split(" ");
			for(String word: towords)
			{
				if(!stoplist.contains(word))
				{
					if(countwords.containsKey(word))
					{
						countwords.put(word, countwords.get(word)+1);
					}
					else
					{
						countwords.put(word,2);
					}
				}
			}

		}
		return countwords;
	}
	
	public static int numofallwords(ArrayList<String> data , ArrayList<String> stoplist)
	{
		int counter = 0;
		Map<String,Integer> countwords = new HashMap<String,Integer>();
		for(String sentence: data)
		{
			String [] towords = sentence.split(" ");
			for(String word: towords)
			{
				if(!stoplist.contains(word))
				{
						countwords.put(word,2);	
						counter++;
				}
			}

		}
		return counter;
	}
	
	public static int numofDifferentwords(ArrayList<String> data , ArrayList<String> stoplist)
	{
		int counter = 0;
		Map<String,Integer> countwords = new HashMap<String,Integer>();
		for(String sentence: data)
		{
			String [] towords = sentence.split(" ");
			for(String word: towords)
			{
				if(!stoplist.contains(word))
				{
					if(countwords.containsKey(word))
					{
						countwords.put(word, countwords.get(word)+1);
					}
					else
					{
						countwords.put(word,2);
						counter++;
					}
				}
			}
		}
		return counter;
	}
	
	public static int countthenubmerwordsfortype (ArrayList<String> data , ArrayList<String> stoplist, ArrayList<Integer> labels , int oneorzero)
	{
		int counter = 0;
		Map<String,Integer> countwords = new HashMap<String,Integer>();
		int i =0;
		for(String sentence: data)
		{
			if(labels.get(i) == oneorzero)
			{
				String [] towords = sentence.split(" ");
				for(String word: towords)
				{
					if(!stoplist.contains(word))
					{
						counter++;
						if(countwords.containsKey(word))
						{
							countwords.put(word, countwords.get(word)+1);
						}
						else
						{
						countwords.put(word,2);
						}
					}
				}
			}
			i++;
		}
		return counter;
	}
	
	public static Map<String,Integer> countthewordsfortype (ArrayList<String> data , ArrayList<String> stoplist, ArrayList<Integer> labels , int oneorzero)
	{
		Map<String,Integer> countwords = new HashMap<String,Integer>();
		int i =0;
		for(String sentence: data)
		{
			if(labels.get(i) == oneorzero)
			{
				String [] towords = sentence.split(" ");
				for(String word: towords)
				{
					if(!stoplist.contains(word))
					{
						if(countwords.containsKey(word))
						{
							countwords.put(word, countwords.get(word)+1);
						}
						else
						{
						countwords.put(word,2);
						}
					}
				}
			}
			i++;
		}
		return countwords;
	}
	
	public static ArrayList<Integer> checkthelines (ArrayList<String> data , ArrayList<String> stoplist, ArrayList<Integer> labels, ArrayList<String> newlines)
	{
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		Map<String,Integer> countwordsfortypeone =  countthewordsfortype(data , stoplist , labels , 1);
		Map<String,Integer> countwordsfortypezero =  countthewordsfortype(data , stoplist , labels , 0);
//		Map<String,Integer> countwordsforall =  countthewords(data , stoplist);
		int numofwordsforone = (countthenubmerwordsfortype(data , stoplist , labels , 1))+ numofallwords(data , stoplist) ;
		int numofwordsforzero = (countthenubmerwordsfortype(data , stoplist , labels , 0)) + numofallwords(data , stoplist) ;
		for(String sentence: newlines){
			double oddsforclass1=0;
			double oddsforclass0=0;
			String [] towords = sentence.split(" ");
			for(String word: towords)
			{
				if(!stoplist.contains(word)){
					if(countwordsfortypeone.containsKey(word))
					{
						oddsforclass1 += countwordsfortypeone.get(word);
					}
					else
					{
						oddsforclass1 += 1;
					}
					if(countwordsfortypezero.containsKey(word))
					{
						oddsforclass0 += countwordsfortypezero.get(word);
					}
					else
					{
						oddsforclass0 += 1;
					}
				}
			}
			oddsforclass1 = oddsforclass1/numofwordsforone;
			oddsforclass0 = oddsforclass0 / numofwordsforzero;
			if(oddsforclass1 > oddsforclass0)
			{
				toReturn.add(1);
			}
			else
				toReturn.add(0);
		}
		return toReturn;
	}
	
	public static void checkprecentage(ArrayList<Integer> naive ,ArrayList<Integer> original)
	{
		double sum = 0;
		int i = 0;
		for(int num: naive)
		{
			if(i< original.size())
			if (num == original.get(i))
			{
				sum++;
			}
			i++;
		}
		System.out.println("The precentage of accuracy is "+ (sum/original.size()));
	}
	
	public static void checkOpposites(ArrayList<String> data , ArrayList<String> stoplist, ArrayList<Integer> labels)
	{
		Map<String,Integer> countwordsfortypeone =  countthewordsfortype(data , stoplist , labels , 1);
		Map<String,Integer> countwordsfortypezero =  countthewordsfortype(data , stoplist , labels , 0);
		Map<String,Integer> countwordsforall =  countthewords(data , stoplist);
		Map<String , Double> topzero = new HashMap<String , Double>();
		Map<String , Double> topone = new HashMap<String , Double>();
		ArrayList<Double> zero = new ArrayList<Double>();
		ArrayList<Double> one = new ArrayList<Double>();
		for(String word: countwordsforall.keySet())
		{
			if(!stoplist.contains(word)&&(countwordsfortypezero.containsKey(word))&&
					(countwordsfortypeone.containsKey(word))){
			Double zeroodds = 1.0;
			Double oneodds  = 1.0;
				zeroodds =	countwordsfortypezero.get(word)*1.0/countwordsforall.get(word);
			oneodds = (double) (countwordsfortypeone.get(word)*1.0/countwordsforall.get(word));
			topzero.put(word ,zeroodds) ;
			topone.put(word, oneodds) ;
			}
		}
		zero.addAll(topzero.values());
		Collections.sort(zero);
		one.addAll(topone.values());
		Collections.sort(one);
		System.out.println("Top 20 smart words ");
		for(int i =1; i<21;)
		{
			for(String word: topzero.keySet())
			{
				if(topzero.get(word).equals(zero.get(zero.size()-i)))
				{
					System.out.print(word + ", ");
//					topzero.remove(word);
					i++;
					if(i>=21)
						break;
				}
			}
		}
		System.out.println();
		System.out.println("Top 20 words of messages that predict the future ");
		for(int i =1; i<21;)
		{
			for(String word: topone.keySet())
			{
				if(topone.get(word).equals(one.get(one.size()-i)))
				{
					System.out.print(word + ", ");
//					topzero.remove(word);
					i++;
					if(i>=21)
						break;
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner trainlabelsscanner = new Scanner(new File("trainlabels.txt"));
		Scanner testlabelsscanner = new Scanner(new File("testlabels.txt"));
		Scanner traindatascan = new Scanner(new File("traindata.txt"));
		Scanner testdatascan = new Scanner(new File("testdata.txt"));
		Scanner stoplistscan= new Scanner (new File("stoplist.txt"));
		
		ArrayList<Integer> trainlabels = new ArrayList<Integer>();
		ArrayList<Integer> testlabels = new ArrayList<Integer>();
		ArrayList<String> traindata = new ArrayList<String>();
		ArrayList<String> testdata = new ArrayList<String>();
		ArrayList<String> stoplist = new ArrayList<String>();
		
		while(trainlabelsscanner.hasNextInt()){
			trainlabels.add(trainlabelsscanner.nextInt());
		}
		while(testlabelsscanner.hasNextInt()){
			testlabels.add(testlabelsscanner.nextInt());
		}
		while(traindatascan.hasNext()){
			traindata.add(traindatascan.nextLine());
		}
		while(testdatascan.hasNext()){
			testdata.add(testdatascan.nextLine());
		}
		while(stoplistscan.hasNext()){
			stoplist.add(stoplistscan.nextLine());
		}
		System.out.println("Question 1");
		ArrayList<Integer> q1newlabels = checkthelines(traindata ,stoplist , trainlabels , traindata);
		System.out.println(trainlabels.toString());
		System.out.println(q1newlabels.toString());
		checkprecentage(q1newlabels ,trainlabels ); 
		System.out.println("Question 2");
		ArrayList<Integer> newlabels = checkthelines(traindata ,stoplist , trainlabels , testdata);
		System.out.println(newlabels.toString());
		System.out.println(testlabels.toString());
		checkprecentage(newlabels ,testlabels ); 
		checkOpposites(traindata , stoplist,  trainlabels);
//		System.out.println(countwords.toString());

//		System.out.println(stoplist.toString());
//		System.out.println(testdata.toString());

//		System.out.println(trainlabels.toString());

		
		 
	}
}
