# compose練習_相關APP_使用到的技術說明


## 標配
MVVM
Hilt


## prac_android_compose
### Material2
  * 側選單
### Service 服務: 伺服端
  * 與prac_character進行雙方溝通
### Broadcase 廣播
  * 接收廣播:低電量
  * 發送廣播:me.wkai.NOTICE_ME_SENPAI
### Sharesheet:發送參數並開啟其他App


## prac_character
### Material2
  * 側選單
  * 下拉刷新
### Retrofit + Moshi + Room
  * 整合Retrofit+Room (在repository:下載與資料庫整合後再取用)
### Service 服務: 客戶端
  * 與prac_android_compose進行雙方溝通
### Broadcase 廣播
  * 接收廣播:me.wkai.NOTICE_ME_SENPAI
### Sharesheet:接收參數並開啟


## prac_fads_demo
### Retrofit + Moshi
  * 解析複雜的json格式，有使用轉換器


## prac_noteApp
### Material2
  * 動畫等應用
### Room

