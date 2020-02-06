package com.wonx.small;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Tessplit {

	public static void main(String[] args) {
		String x = "xxx|ffff|ttttt";
//		x = x.replaceAll("\\|", "~");
//		System.out.println(x);
		System.out.println(ReflectionToStringBuilder.toString(x.split("\\|"), ToStringStyle.MULTI_LINE_STYLE));

	}

}
