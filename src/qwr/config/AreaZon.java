package qwr.config;

import qwr.Item;
import qwr.Loger;

import java.util.ArrayList;
import java.util.List;

import static qwr.Loger.prnq;
import static qwr.Loger.prnt;
import static qwr.SharSystem.UtilFile.sepr;

public class AreaZon implements Item {//область,зона
	int 	idArea;//ИД зона
	String 	titulArea;//наименование
	int		pipl;//количество людей в зоне

	static public List<Item> list = new ArrayList<>();
	static {//инициализация класса
		list.add(new AreaZon(0));
	}

	private AreaZon(int idArea, int pipl, String titulArea) {//для тестирования
		this.idArea = idArea;
		this.titulArea = titulArea;
		this.pipl = pipl;
	}

	AreaZon(int id){idArea=id; titulArea=""; pipl=0;}

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
	public Item addID(int id) { return new AreaZon(id); }

	@Override
	public String title() { return titulArea; }

	@Override
	public void setTitle(int j, String x) { titulArea=x; }

	@Override
	public String printLn() { return idArea+"\t( "+pipl+" )\t"+titulArea; }

	@Override
	public String printHd() { return "№\tлюдей\t\tНазвание зоны"; }

	@Override
	public String printTitle() { return "Зоны охраны объекта"; }
	/**
	 * Возвращение ссылки на список данного класса
	 * Вызывается из LoadExternDataThead.workIntegrate
	 * @return ссылка на список данного класса
	 */
	public List<Item> linkList(){ return list; }
	public static final int sizeAr=4;//9 количество полей в текстовом файле данных
	/**
	 * Создание элемента нужного типа для помещения в очередь новых элементов
	 * полученных из внешних файлов. Вызывается из GrRecords.readRecordExt
	 * @param words срока из файла внешних данных
	 * @param src номер элемента в enum GrRecords
	 * @return новый элемент
	 */
	public static Item creatExtDbf(String[] words, int src) {
		if (words.length < sizeAr) {
			for (int i = 0; i < words.length; i++) prnt("+  "+i+"-"+words[i]);prnq("~"+words.length);
			return null; //недостаточное количество элементов
		}
		AreaZon z;
		try { z=new AreaZon(Integer.parseInt(words[1]),Integer.parseInt(words[2]), words[3]);
		}
		catch (Exception ex) {ex.printStackTrace();return null;}
		if (list.isEmpty()) return z;
		for (Item x : list) if (z.equals((AreaZon) x)) return null;
		return z;
	}//creatExtDbf

	@Override
	public String toString() {//создание строки для записи в текстовый файл
		return sepr+ idArea + sepr+ pipl + sepr+ titulArea + sepr;
	}//toString

}//class Area
