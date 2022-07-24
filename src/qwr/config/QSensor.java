package qwr.config;
/**
 * Класс описывающий динамическое состояние датчиков
 */
public class QSensor {
	boolean	actions;//активность датчика
	int		reading;//текущее показание датчика (-2 обрыв, -1 КЗ, 0- норма, 1-сработка)
	int		time;	//последнее время срабатывания датчика
}//class QSensor
