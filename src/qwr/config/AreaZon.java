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
	AreaZon(int id){idArea=id; titulArea=""; pipl=0;}

	static public void init(){
		list.add(new AreaZon(0));
	}//init

	@Override
	public int idItem() { return idArea; }

	@Override
	public Item addID(int id) {
//		Loger.logs("id:"+id);
		return new AreaZon(id); }

	@Override
	public String titul() { return titulArea; }

	@Override
	public void setTitul(String x) { titulArea=x; }

	@Override
	public void printList() {
		for (Item x:list ) { Loger.prnq(x.printLn()); }
		Loger.prnq("---"+list.size());
	}//printList

	@Override
	public String printLn() { return idArea+"\t( "+pipl+" )\t"+titulArea; }

	@Override
	public String printHd() { return "№\tлюдей\t\tНазвание зоны"; }

	@Override
	public String printTitul() { return "Зоны охраны объекта"; }
//
//	@Override
//	public int adding(int jbeg, int jend) {
//		if (jbeg==0) return 0;
//		int z=0;
//		if (jend==0) if (addn(jbeg)) return 1;
//		else return 0;
//		else
//			for (int i = jbeg; i <= jend ; i++) if (addn(i)) z++;
//		return z;
//	}//adding
//	private boolean addn(int j){
//		for (Item x:list ) if (x.idItem()==j) return false;
//		list.add(addID(j));
//		return true;
//	}//addn
//
//
//	@Override
//	public int removal(int jbeg, int jend) {
//		if (jbeg==0) return 0;
//		int z=0;
//		if (jend==0) if (remov(jbeg)) return 1;
//					else return 0;
//		else
//			for (int i = jbeg; i <= jend ; i++) if (remov(i)) z++;
//		return z;
//	}//removal
//
//	private boolean remov(int j){
//		Item z=null;
//		for (Item x:list ) {
//			if (x.idItem()==j) {
//				z=x;
//				break;
//			}
//		}
//		if (z != null) list.remove(z);
//		else return false;
//		return true;
//	}//remov
}//class Area
