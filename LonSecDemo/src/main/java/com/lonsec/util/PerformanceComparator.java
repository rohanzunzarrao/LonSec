package com.lonsec.util;

import java.util.Comparator;

import com.lonsec.model.FundPerformance;

/**
 * Comparator for the FundPerformance objects.
 * <br></br>
 * It sorts them based on date ascending and fund return descending.
 * @author Rohan Z
 *
 */
public class PerformanceComparator implements Comparator<FundPerformance> {

	public int compare(FundPerformance o1, FundPerformance o2) {
		if(o1 == null && o2 == null)
			return 0;
		else if(o1 == null)
			return -1;
		else if(o2 == null)
			return 1;
		
		int dateCompare = o1.getDate().compareTo(o2.getDate());
		if(dateCompare != 0)
			return dateCompare;
		else
			return o2.getFundReturn().compareTo(o1.getFundReturn());
	}
}
