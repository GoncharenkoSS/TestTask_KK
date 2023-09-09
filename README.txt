Добрый день, 

Доступные методы для задач:

GET:    /api/tasks                -Получить список всех задач.
	

GET:	/api/tasks/{id}           -Поиск задачи по ID.
	



PATCH:	/api/tasks/{id}           -Метод назначает исполнителя на задачу по имени.
	
	{"name": "AAA"}


PATCH:	/api/change-task/{id}     -Метод меняющий задачу по ID (все кроме id и performer).
	
	{"title": "AAA",
	"description": "AAA",
	"status": "AAA"}


POST:   /api/tasks                -Создать задачу
	
	{"title": "AAA",
	"description": "AAA",
	"status": "AAA"}


Доступные методы для работников:

GET:    /api/workers              -Получить список всех работников.


GET:    /api/workers/{id}         -Найти работника по ID.


POST:   /api/workers              -Создать работника.

	{"name": "AAA",
	"position": "AAA",
	"avatar": "AAA"}


PATCH:   /api/workers/{id}        -Обновить данные о работнике по ID.

	{"name": "AAA",
	"position": "AAA",
	"avatar": "AAA"}


DELETE:  /api/workers/{id}         -Удалить работника по ID.


DELETE:  /api/workers/             -Удалить всех работников.


P.S.
Не знал как поступить с полем "time", решил добавить автоматическое время создания задачи.
Для автоматического создания БД использовал FlyWay. 

В корне проекта есть само задание и SQL запросы для тестов.

P.P.S.
Есть ощущение, что я нагородил с многопоточностью...))


