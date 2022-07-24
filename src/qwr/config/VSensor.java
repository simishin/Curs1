package qwr.config;

import qwr.Item;
import qwr.Loger;
import qwr.XFields;
import qwr.sysType.ObjectStatus;
import qwr.sysType.TypeObject;
import qwr.sysType.TypeSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class VSensor implements Item {
//	static private int count = 0;
private final int id;
	private int jTyp;
	private String title;

	static XFields[] uField = new XFields[2];//список полей для генерации элементов
	private static final String[] uTyp = {"Дискретный", "Инверсный дискретный", "С тремя состояниями",
			"С тремя состояниями инверсный", "С четырьмя состояниями",
			"С четырьмя состояниями инверсный"};

	static {//инициализация класса
		uField[0] = new XFields(1, "тип диагностики", "", uTyp);
		uField[1] = new XFields(1, XFields.Typ.STRING, "Обозначение", "", 1, 35);
	}//инициализация класса

	static public List<Item> list = new ArrayList<>();

	public VSensor(int id, int jTyp, String title) {
		this.id = id;
		this.jTyp = jTyp;
		this.title = title;
	}

	@Override
	public String printLn() {return id + "\t" + uTyp[jTyp] + "\t" + title;	}

	@Override
	public String printHd() {return "№\tТип выхода\tтип датчика";}

	@Override
	public String printTitle() { return "Типы применяемых датчиков";}

	@Override
	public int idItem() { return id;	}

	@Override
	public Item addID(int id) {
		VSensor y = (VSensor) list.get(0);
		int x0 = uField[0].isUse() ? y.jTyp : 0;
		String x1= uField[1].isUse() ? y.title : "";
//		int x0 = uField[0].isUse() ? uField[0].let() : 0;
//		String x1= uField[1].isUse() ? uField[1].lets() : "";
		return new VSensor(id,x0,x1);
	}//addID-----------------------------------------------
	/**
	 * модификация элемента списка по шаблону, находящемся в нулевом элементе
	 */
	@Override
	public void update(int index){
		VSensor y = (VSensor) list.get(0);
		if (uField[0].isUse()) this.jTyp = y.jTyp;
		if (uField[1].isUse()) this.title = y.title;
//		if (uField[0].isUse()) this.jTyp = uField[0].let();
//		if (uField[1].isUse()) this.title = uField[1].lets();

	}//update---------------------------------------------------------
	/**
	 * Выводит на экран установленные фильтры для конкретного списка элементов
	 * Вызывается из Dialog.printDefine(List<Item> list)
	 * и передает в XFields.printDefine(uField) массив данных нулевого элемента
	 */
	@Override
	public void prnStencil(){
		assert this.id == 0 : "Этот метод вызван не для нулевого элемента";
		VSensor y = (VSensor) list.get(0);
		uField[0].let(y.jTyp);
		uField[1].let(y.title);
		XFields.prnStencil(uField);
	}//printDefine
	/**
	 * Вызывается из Dialog.editDef(Scanner con, List<Item> list, int index)
	 * @param con консоль
	 * @return транслирует данные из XFields.uConsol(con,uField,""), где
	 * истина - выход на верхний уровень меню, лож- продолжение редактирования списка
	 */
	@Override
	public boolean uConsol(int index, Scanner con) {
		Loger.logs(" idRoom:"+this.id);
		VSensor z = (VSensor) list.get(0);
		VSensor y = (VSensor) this;
		uField[0].let(y.jTyp);
		uField[1].let(y.title);

//		int x0 = uField[0].let();
//		String x1=uField[1].lets();
//		boolean k0=uField[0].isUse();
//		boolean k1=uField[1].isUse();
//		VSensor y = (VSensor)this;
//		uField[0].let(y.jTyp);
//		uField[1].let(y.title);
//		uField[2].let(y.kClassRom);
		boolean q = XFields.uConsol(con,uField,"");
		y.jTyp =uField[0].let();
		y.title =uField[1].lets();
		z.jTyp =uField[0].let();
		z.title =uField[1].lets();
//
//		y.jTyp=uField[0].let();
//		y.title=uField[1].lets();
//		uField[0].let(x0);
//		uField[1].let(x1);
//		uField[0].let(k0);
//		uField[1].let(k1);
//		y.kClassRom = uField[2].let();
//		Room z = (Room)list.get(0);
//		z.bild=uField[0].lets();
//		z.floor=uField[1].let();
//		z.kClassRom = uField[2].let();
		return q;
	}//uConsol---------------------------------------------------------




	@Override
	public void setTitle(int j, String x) { title=x; }

	static public void init() { list.add(new VSensor(0, 0, "")); }
	public String title() {
		return title;
	}

}//record VSensor