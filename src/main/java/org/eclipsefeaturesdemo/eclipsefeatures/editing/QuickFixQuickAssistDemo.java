package org.eclipsefeaturesdemo.eclipsefeatures.editing;

import java.util.ArrayList;
import java.util.List;

public class QuickFixQuickAssistDemo {

	public static void main(String[] args) {
		List<String> items = new ArrayList<String>();
		items.add("one");
		items.add("two");
		items.add("three");
		
		int count = items.size();
		if (count > 1) {
		System.out.println("Multiple items found");
		}
		
		printItems(items);
		
		String firstItem = items.get(0);
		
		

		
	}

	private static void printItems(List<String> items) {
		for (String item : items) {
			System.out.println(item);
		}
	}

}
