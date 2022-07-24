package qwr.config;

import qwr.Item;
import qwr.XFields;

import java.util.ArrayList;
import java.util.List;

public record RSensor(int id, int jTyp, String title) implements Item {
	static  private  int count=0;
	static XFields[] uField = new XFields[2];//список полей для генерации элементов
	private static final String[] uTyp = {"Дискретный","Инверсный дискретный","С тремя состояниями",
			"С тремя состояниями инверсный", "С четырьмя состояниями",
			"С четырьмя состояниями инверсный" };

	static{//инициализация класса
		uField[0] =new XFields(1, "тип диагностики","", uTyp);
		uField[1] =new XFields(1, XFields.Typ.STRING,"Обозначение","",1,35);
	}//инициализация класса

	static public List<Item> list = new ArrayList<>();

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
	static public void init(){ list.add(new RSensor(0,0,"")); }

}
