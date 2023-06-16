# Покупай — Помогай
Благотворительный проект Александра Кулагина, разработанный в рамках сотрудничества IT школы Samsung и X5 Group.

<a href="https://codeclimate.com/github/IFraimG/nofoodsharingproject_x5/maintainability"><img src="https://api.codeclimate.com/v1/badges/43002fad82fd9eabe136/maintainability" /></a>
[![Android CI](https://github.com/IFraimG/nofoodsharingproject_x5/actions/workflows/android.yml/badge.svg)](https://github.com/IFraimG/nofoodsharingproject_x5/actions/workflows/android.yml)

Пожалуйста, проверьте страницу [CONTRIBUTING](CONTRIBUTING.md), если вы хотите помочь.


![logotype](https://github.com/IFraimG/nofoodsharingproject_x5/blob/master/app/src/main/res/drawable-v24/rectangle_2.png)

## Основная идея проекта:
Создание приложения для людей, которые располагают возможностью помочь малообеспеченным, удобно совершать пожертвоания.


## Техническое задание:
- Создать мобильное приложение
- В приложении быть два типа аккаунтов "Отдающий" и "Принимающий".
- Создана система авторизации, профили и тд.
- "Принимающий" пользователь имеет возможность создавать объявления о его нужде в каких-то продуктах первой необхости в магазине. Также, он должен быть ограничен в запросе продуктов - не более трёх в одном объявлении.
- "Принимающий" пользователь имеет возможность выбирать ему удобный ему магазин и прикрепляться к нему для создания объявления.
- "Принимающий" пользователь имеет возможность удаления объявления.
- "Отдающий" пользователь может просматривать объявления от "Принимающих" в магазине, к которому он прикреплён.
- В радиусе 50м от магазина "Отдающему" пользователю должно приходить push уведомление о возможности совершения пожертвования в близлежайшем магазине.
- В приложении "Отдающего" пользователя должен генерироваться код, который должен быть написан на пакете, для того, чтобы "Принимающий" пользователь мог забрать продукты.
- "Отдающий" пользователь должен иметь возможность сообщить о пожертвовании "Принимающему" пользователю. Создание чата.
- Оба типа пользователей должны иметь возможность редактирования данных своего аккаунта.
- Объявление "Принимающего" пользователя перестаёт существовать при закрытии магазина.
- Также реализованна система фильтрации объявлений.
- "Принимающему" пользователю также должны приходить push уведомления об изменении статуса его объявления.
- Для "Отдающего" пользователя должна быть реализована система поощрений (В данный момент - vk чекбек, в дальнейшем - баллы на карту магазина x5 group).
- Оба типа пользователей должны иметь возможность просматривать на карте близлежайшие к ним магазины, учавствующие в проекте, а также строить маршрут до них.
- Также пользователи имеют возможность посмотреть историю передач.
- Время, в течение которого "Принимающий" пользователь может забрать продукты из магазина – 2 часа с момента пожертвования "Отдающего" пользователя.
- Время, в течение которого уведомление о "Нуждающемся" отправляется один раз – 2 часа
- Ограничение работы уведомлений и создание объявлений – 23:00 - 10:00 (или время открытия магазина, к которому прикреплён "нуждающийся" пользователь).


## Устройство кода проекта
#### Языки
```
java 17
xml
javaScript
```
#### Технологии
##### Android (SDK 24+):
- Архитектура MVVM
- Dagger (DI)
- Navigation graph
- Recyclerview
- Retrofit2
- Yandex Mapkit
- Firebase Cloud Messaging
- Encrypted Sharedpreferences
- Socket-io java client
- Data/View binding
- Glide
- Android CI
- Instabug
- Leakcanary
- Notification Service
- Location Service

##### Node.js (Express.js):
- Архитектура MVC
- MongoDB (Mongoose)
- Passport.js (Json Web Token)
- Bcrypt.js
- Socket.io
- Nodemon
- Day.js
- Docker


#### Данные
Для хранения данных о аккаунте пользователя непосредственно на устройстве используются SharedPreferences и encrypted SharedPreferences.
Данные о объявлениях "Принимающих" пользователей, их статусах, аккаунтах, чатах и тд хрянятся на сервере и испозуются в приложении с помощью фреймворка Retrofit. Хранение основных данных происходит в MongoDB, а через Socket.io реализована работа чата в приложении. Шифрование данных - Bcrypt.js.

#### Реализация (Регулярно обновляется)
###### Java

| Android folder | meaning |
| -- | ------ |
| data| api, models|
| domain | helpers, utils |
| presentation| работа UI |


###### data:
| Models | meaning                          |
| ------ |-----------------------------------|
| Advertisment| Модель обявлений                  |
| Chat | Модель чатов                      |
| Faq | Модель faq                        |
| Needy | Модель "принимающих" пользователей |
| LoaderStatus | Модель статуса загрузки           |
| Market | Модель магазинов                  |
| Message | Модель сообщений                  |
| Notification | Модель уведомлений                |
| Giver| Модель "отдающих" пользователей   |
| ShortDataUser | Упрощенная модель User    |
| User | Базовая модель пользователя               |

###### domain:
| Android folder | meaning |
|---------------| -- |
| helpers | Вспомогательные классы |
| utils  | Утилиты |


###### presentation:
| Android folder | meaning |
|---------------| -- |
| activities |   Активности |
| adapters  | Адаптеры |
| di | dependency injection |
| factories | Фабрики |
| fragments | Фрагменты |
| services | Сервисы |
| viewmodels | Вью модели |
| views | Кастомизация элементов |

| Adapter | meaning |
| ------ | ------ |
| FaqAdapter | Адаптер для Faq |
| NeedyNotificationsAdapter | Адаптер уведомлений "принимающего" пользователя|
| MessagesAdapter | Адаптер сообщений|
| GiverAdvertListAdapter | Адаптер списка объявлений у "отдающего" пользователя |

###### Xml
- /res/values/ - Вынесены все повторяющиеся элементы дизайна
- /res/strings/ - Хранение всего текста
- /res/dimens/ - Вынесены все основные dp/sp размеры
- /res/fonts/ - Кастомные шрифты x5 group

## Политика конфедициальности
Александр Кулагин создал приложение Покупай-помогай в качестве бесплатного приложения при участии и помощи Григория. Эта услуга предоставляется Александром бесплатно и предназначена для использования как есть. Эта страница используется для информирования посетителей о наших правилах сбора, использования и раскрытия Личной информации, если кто-то решил воспользоваться моим Сервисом. Если вы решите использовать наш Сервис, вы соглашаетесь на сбор и использование информации в связи с этой политикой. Личная информация, которую я собираю, используется для предоставления и улучшения Сервиса. Я не буду использовать или делиться вашей информацией с кем-либо, кроме случаев, описанных в настоящей Политике конфиденциальности. Сбор и использование информации:
Для лучшего опыта при использовании нашего Сервиса я могу потребовать от вас предоставить нам определенную личную информацию, включая, помимо прочего, геолокацию и состояние телефона. Информация, которую я запрашиваю, будет сохранена на вашем устройстве и никоим образом не будет собираться мной.
Приложение использует сторонние сервисы, которые могут собирать информацию, используемую для вашей идентификации.
Изменения в настоящей Политике конфиденциальности:
Я могу время от времени обновлять нашу Политику конфиденциальности. Таким образом, вам рекомендуется периодически просматривать эту страницу на предмет любых изменений. Я сообщу вам о любых изменениях, опубликовав новую Политику конфиденциальности на этой странице. Эти изменения вступают в силу сразу после публикации на этой странице.
Если у вас есть какие-либо вопросы или предложения относительно моей Политики конфиденциальности, не стесняйтесь обращаться ко мне по адресу pokupaypomogay@mail.ru .


*Приятного пользования!*
