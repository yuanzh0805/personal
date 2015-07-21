package cn.sdu.utils;

import java.util.List;

import cn.sdu.domain.Novel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonToBean {

	private static Gson gson = new Gson();
	
public static List<Novel> toListNovels(String s){
		
		List<Novel> listNovels = gson.fromJson(s, new TypeToken<List<Novel>>() {  
        }.getType());
		
		return listNovels;
		
		
	}
	
	/*
	 * 转化成类
	 */
	public static Novel toNovel(String s){
		
		Novel novels = gson.fromJson(s, Novel.class);
		
		return novels;
		
	}
}
