# Flight-Entertainment-System

一. 由于又增加了一些带路径的方法, 所以这次统一了下路径, 但还是有两处路径需要修改, 改成自己电脑中Movies文件夹的位置

    1.HomePage类, 修改filePath变量的默认值

    2.MovieController类, initialize方法中的路径

二. 此次添加了css, 目前的实现是从文件夹中读取第一个css文件作为程序的默认配置文件, 并且可以让乘客随时切换文件夹中的前三个css文件 所以需要在自己电脑中新建一个CSS文件夹, 并将stylesheet.css放入该文件夹(文件夹中至少要有3个css文件, 否则会报错), 仍有两处路径需要修改

    1.HomePage类, 修改cssPath变量的默认值
    
    2.RootLayoutController类, 修改文件的路径名
    
