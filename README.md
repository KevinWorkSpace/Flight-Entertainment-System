# Flight-Entertainment-System

一. 有一处路径需要修改, 改成自己电脑中Movies文件夹的位置

    HomePage类, 修改filePath变量的默认值

二. 此次添加了css, 需要在自己电脑中新建一个CSS文件夹, 并将stylesheet.css放入该文件夹(文件夹中至少要有3个css文件, 否则会报错), 仍有一处路径需要修改, 改成自己电脑中CSS文件夹的位置

    HomePage类, 修改cssPath变量的默认值
    
三. 实现了电影分类, 所以需要在Movies文件夹下再建四个文件夹: Funny, Fiction, Cartoon, Romance 并把原来放在Movies文件夹中的电影分类放入这些文件夹下

四. 关于数据库, 不太清楚如何远程连到我的数据库上面, 所以暂时请用自己电脑上的MySQL数据库, 有几处需要修改
    
    1. Homepage类, url中的数据库名字, 用户名, 密码
    2. 数据库需要建立movies表, 有三个字段: idmovies(主键, 要设成auto-increment), name, 和info
    3. 如果不了解数据库的使用, 请直接注释掉数据库的代码即可
    
五. 关于电影信息的爬取, 爬取工具已经上传在ScrapingUtil中

六. 关于logo以及背景图片

    1. 要建一个Resource文件夹, 并在里面再建logo文件夹和image文件夹, logo文件夹中放入logo图片, image文件夹中放入背景图片
    2. HomePage类, 修改logoPath变量的默认值
