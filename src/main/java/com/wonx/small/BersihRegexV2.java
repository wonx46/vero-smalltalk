package com.wonx.small;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BersihRegexV2 {
	
	
	
	private static String tablename;

	public static void main(String[] args) throws IOException {
		tablename = args[0];

		
		berishinFile(args[1]);
		File file = new File("fileclean.txt");
		List<String> string = FileUtils.readLines(file);
		List<String> lsql =new ArrayList<String>();
		List<String> ltimestampnull =new ArrayList<String>();
		
		
		String sql = "";
		File fileOut = new File(args[2]);
		File fileErr = new File("error.log");
		
		int i=1;
		for (String string2 : string) {
			System.out.println("Line ke "+i);
			if(i>1){
				String tmp1 = string2.replaceAll("\"", "");
				String[] split = tmp1.split("\\|");
				
//				System.out.println("split: "+ReflectionToStringBuilder.toString(split, ToStringStyle.MULTI_LINE_STYLE));
				T_ai_message o = new T_ai_message();
				if(split.length>13){
					o = getObjGaNormal(split);
				}else{
					o = getObjNormal(split);
				}
					
				if(o.getTimestamp()==null || o.getTimestamp().equalsIgnoreCase("null")){
					ltimestampnull.add("Line ke "+i+", id: "+String.valueOf(o.getId()));
				}
				
//				System.out.println(ReflectionToStringBuilder.toString(o, ToStringStyle.MULTI_LINE_STYLE));
				sql = objToSql(o);
				lsql.add(sql);				
			}

			
			i++;
			
		}
		
		
		
		FileUtils.writeLines(fileOut, lsql);
		System.out.println("done: "+i);
		FileUtils.writeLines(fileErr, ltimestampnull);
		
		
	}

	private static void berishinFile(String filename) {
		System.out.println("Cleaning File csv..");
		File file = new File(filename);
		File fileClean = new File("fileclean.txt");
		List<String> hasil = new ArrayList<String>();
		try {
			List<String> string = FileUtils.readLines(file);
			hasil.add(string.get(0)); //header
			String tmp = "";
			for (int i=1;i<string.size();i++) {
				System.out.println(i+"/"+string.size());
				if(i==1){
					hasil.add(string.get(i));
				}else{
					tmp = hasil.get(hasil.size() - 1);
//					tmp = string.get(i-1);
					if(!tmp.contains("message_id")){
						if(!isContainsId(tmp) ){
							
							tmp = tmp + string.get(i);
							
							
							if(tmp.contains("\\n\\n")){
								tmp = tmp.replaceAll("\\\\\n\\\\\n", "");
							}
							
							if(tmp.contains("\\n")){
								tmp = tmp.replaceAll("\\\\\n", "");
							}
							
							if(tmp.contains("\\?\\n")){
								tmp = tmp.replaceAll("\\\\?\\\\n", "");
							}
							
//							System.out.println(tmp);
							hasil.set( hasil.size() - 1, tmp);	
						}else{
							hasil.add(string.get(i));
						}
						
					}
				}
				

		
//				if(string.get(i).contains("\\n\\n")){
//					hasil.add(string.get(i).replaceAll("\\\\\n\\\\\n", ""));
//				}else if(string.get(i).contains("\\n")){
//					tmp = hasil.get(hasil.size() - 1);
//					tmp = tmp +" "+ string.get(i).replaceAll("\\\\n", "");
//					for(int x=i+1;x<string.size();x++){
//						if(string.get(x).contains("\\n")){
//							tmp = tmp +" "+ string.get(x).replaceAll("\\\\n", "");
//						}else{
//							i = x - 1;
//							break;
//						}
//					}
//					hasil.set( hasil.size() - 1, tmp);
//				}else if(!string.get(i).contains("\"payload\":{\"") && (i-1)!=0){
//					tmp = hasil.get(hasil.size() - 1);
//					if(tmp.contains("\"payload\":{\"")){
//						hasil.add(string.get(i));
//					}else{
//						tmp = tmp +" "+ string.get(i);
//						for(int x=i+1;x<string.size();x++){
//							if(!string.get(x).contains("\"payload\":{\"") ||string.get(x).length()<=10 ){
//								tmp = tmp +" "+ string.get(x);
//							}else if(isContainsId(string.get(x))){
//								tmp = tmp +" "+ string.get(x);
//								i = x;
//								break;
//							}else{
//								i = x;
//								break;
//							}
//						}
//						hasil.set( hasil.size() - 1, tmp);	
//					}
//					
//				}
//				else if(isContainsId(string.get(i))){
//					tmp = hasil.get(hasil.size() - 1);
//					tmp = tmp + string.get(i);
//					hasil.set( hasil.size() - 1, tmp);	
//				}
//				else{
//					hasil.add(string.get(i));
//				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileUtils.writeLines(fileClean, hasil);
			System.out.println("Cleaning done...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static boolean isContainsId(String s) {
		if(s.length()>10){
			String tmp = s.substring(s.length()-9, s.length()-1);
			try {
				Double d = Double.parseDouble(tmp);
					if( s.charAt(s.length()-10)=='|'){
						return true;
					}	
				
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	private static String objToSql(T_ai_message aut) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 Field[] fields = aut.getClass().getDeclaredFields();
		  String campos="";
		   String valores="";
		   
		   int i=0;
		 for (Field field : fields) {
			try {
				String name = field.getName();
				Object value  = PropertyUtils.getProperty(aut, field.getName());
				if(i != 0){
					 campos=campos+",";
		               valores=valores+",";	
				}
				
				if(value == null){
					 valores=valores+value;
				}else{
					if(value instanceof String){
						String tmp = (String) value;
						if(tmp.equalsIgnoreCase("null")){
							 valores=valores+value;
						}else{
							value = tmp.replaceAll("'", "");
							 valores=valores+"'"+value+"'";
						}
			              
			           }else if(value instanceof Date){
			        	   valores=valores+"'"+formater.format( (Date) value  )+"'";
			           }
			           else{
			               valores=valores+value;
			            }	
				}
				
				 campos=campos+name;
				 
				 i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		
		     String sql="insert into "+tablename+"("+campos+")values("+valores+");";
		   return sql;
	}

	private static T_ai_message getObjGaNormal(String[] split) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		T_ai_message o = new T_ai_message();
		for(int i=0;i<10;i++){
			switch(i){
			case 0: o.setMessage_id(split[i]);
					break;
			case 1: o.setUuid(split[i]);
			break;
			case 2: o.setIntent_id(split[i]);
			break;
			case 3: o.setChannel(split[i]);
			break;
			case 4: o.setSender(split[i]);
			break;
			case 5: o.setLanguage(split[i]);
			break;
			case 6: o.setMessage_type(split[i]);
			break;
			case 7: o.setMessage_text(split[i]);
			break;
//			case 8: 
//				if(!split[i].equalsIgnoreCase("null")){
//					o.setConfidence_score(Double.valueOf(split[i]));	
//				}
//				
//			break;
//			case 9: try {
//					o.setTimestamp(formater.parse(split[i]));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			break;
		
		
			}
		}
		
		int indexTgl = 9;
		 try {
			 Date tmx = formater.parse(split[indexTgl]);
			 o.setTimestamp(split[indexTgl]);
			 o.setConfidence_score(Double.valueOf(split[8]));	
			} catch (ParseException e) {
				indexTgl = cariIndexTgl(9, split);
				
			}
		
		 if(indexTgl>9){
			 o.setTimestamp(split[indexTgl]);
			 o.setConfidence_score(Double.valueOf(split[indexTgl-1]));	
			 String msgTxt = "";
			 for(int i=7;i<indexTgl-1;i++){
				 if(i != indexTgl -2){
					 msgTxt = msgTxt + split[i]+"|";	
					}else{
						msgTxt = msgTxt + split[i];
					}
					
			 }
			 
			 o.setMessage_text(msgTxt);
		 }
		
		
		
		String payload ="";
		for(int i=indexTgl+1;i<split.length -2;i++){
			if(i != split.length -3){
				payload = payload + split[i]+"|";	
			}else{
				payload = payload + split[i];
			}
			
		}
		
		o.setContextPayload(payload);
		o.setFlow(split[split.length-2]);
		o.setId(Integer.parseInt(split[split.length-1]));
		
		return o;
	}
	
	private static int cariIndexTgl(int mulai, String[] split) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(int i=mulai;i<split.length;i++){
			 try {
				Date tmp = formater.parse(split[i]);
				return i;
			 } catch (ParseException e) {
					
					
				}
		}
		
		return 0;
	}

	private static T_ai_message getObjNormal(String[] split) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		T_ai_message o = new T_ai_message();
		for(int i=0;i<split.length;i++){
			switch(i){
			case 0: o.setMessage_id(split[i]);
					break;
			case 1: o.setUuid(split[i]);
			break;
			case 2: o.setIntent_id(split[i]);
			break;
			case 3: o.setChannel(split[i]);
			break;
			case 4: o.setSender(split[i]);
			break;
			case 5: o.setLanguage(split[i]);
			break;
			case 6: o.setMessage_type(split[i]);
			break;
			case 7: o.setMessage_text(split[i]);
			break;
			case 8:
				if(!split[i].equalsIgnoreCase("null")){
				o.setConfidence_score(Double.valueOf(split[i]));	
			}
			
			break;
			case 9:
					o.setTimestamp(split[i]);
			break;
			case 10: o.setContextPayload(split[i]);
			break;
			
			case 11: o.setFlow(split[i]);
			break;
			
			case 12: o.setId(Integer.parseInt(split[i]));
			break;
		
			}
		}
		
		return o;
	}

}
