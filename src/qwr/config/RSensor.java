package qwr.config;

import qwr.Item;
import qwr.XFields;

import java.util.ArrayList;
import java.util.List;

import static qwr.Loger.prnq;
import static qwr.Loger.prnt;
import static qwr.SharSystem.UtilFile.sepr;

public record RSensor(int id, int jTyp, String title) implements Item {
	static  private  int count=0;
	static XFields[] uField = new XFields[2];//список полей для генерации элементов
	private static final String[] uTyp = {"Дискретный","Инверсный дискретный","С тремя состояниями",
			"С тремя состояниями инверсный", "С четырьмя состояниями",
			"С четырьмя состояниями инверсный" };
	static public List<Item> list = new ArrayList<>();
	static{//инициализация класса
		uField[0] =new XFields(1, "тип диагностики","", uTyp);
		uField[1] =new XFields(1, XFields.Typ.STRING,"Обозначение","",1,35);
		list.add(new RSensor(0,0,""));
	}//инициализация класса



	@Override
	public String printLn() { return id+"\t"+uTyp[jTyp]+"\t"+title; }

	@Override
	public String printHd() { return "№\tТип выхода\tтип датчика"; }

	@Override
	public String printTitle() { return "Типы применяемых датчиков"; }

	@Override
	public int idItem() {
		return 0;
	}

	@Override
	public Item addID(int id) {
		return null;
	}

	@Override
	public void setTitle(int j, String x) {

	}

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
//        assert prnq("$ RiUser.creatExtDbf NOT REALISE $");
		if (words.length < sizeAr) {
			for (int i = 0; i < words.length; i++) prnt("+  "+i+"-"+words[i]);prnq("~"+words.length);
			return null; //недостаточное количество элементов
		}
//		int order = src==0 ? Integer.parseInt(words[7]) : ((src & 7)<<29)+count & 268435455;
		VSensor z;//this(key, login, titul, descr, user, pass, change, count++);
		try { z=new VSensor(Integer.parseInt(words[1]),Integer.parseInt(words[2]), words[3]);
		}
		catch (Exception ex) {ex.printStackTrace();return null;}
		if (list.isEmpty()) return z;
		for (Item x : list) if (z.equals((VSensor) x)) return null;
		return z;
	}//creatExtDbf

	@Override
	public String toString() {//создание строки для записи в текстовый файл
		return sepr+ id + sepr+ jTyp + sepr+ title + sepr;
	}//toString

}
