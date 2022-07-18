package qwr.config;

import qwr.Item;
import qwr.Loger;

import java.util.ArrayList;
import java.util.List;

public class AreaZon implements Item {//область,зона
	int 	idArea;//ИД зона
	String 	titulArea;//наименование
	int		pipl;//количество людей в зоне

	static public List<Item> list = new ArrayList<>();

	private AreaZon(int idArea, int pipl, String titulArea) {//для тестирования
		this.idArea = idArea;
		this.titulArea = titulArea;
		this.pipl = pipl;
	}

	AreaZon(int id){idArea=id; titulArea=""; pipl=0;}

	static public void init(){
		list.add(new AreaZon(0));
	}//init

	public static void test() {//создание набора данных для тестирования
		list.add(new AreaZon(1,12,"qwer1"));
		list.add(new AreaZon(2,22,"qwer2"));
		list.add(new AreaZon(3,32,"qwer3"));
		list.add(new AreaZon(4,42,"qwer4"));
		list.add(new AreaZon(5,52,"qwer5"));
		list.add(new AreaZon(6,62,"qwer6"));
		list.add(new AreaZon(7,72,"qwer7"));
		list.add(new AreaZon(8,82,"qwer8"));
	}//test

	@Override
	public int idItem() { return idArea; }

	@Override
	public Item addID(int id) {
//		Loger.logs("id:"+id);
		return new AreaZon(id); }


	@Override
	public String title() { return titulArea; }

	@Override
	public void setTitle(String x) { titulArea=x; }

//	@Override
//	public void printList() {
//		for (Item x:list ) { Loger.prnq(x.printLn()); }
//		Loger.prnq("---"+list.size());
//	}//printList

	@Override
	public String printLn() { return idArea+"\t( "+pipl+" )\t"+titulArea; }

	@Override
	public String printHd() { return "№\tлюдей\t\tНазвание зоны"; }

	@Override
	public String printTitle() { return "Зоны охраны объекта"; }

}//class Area
