nutz-web-archetype
==================

使用Maven的Archetype插件，方便快速创建nutz web项目
使用方法
1、mvn install
2、 mvn archetype:generate -DarchetypeCatalog=local
交互输入如下信息：

选择nutz-web-archetype模板
2.1、输入groupId, 如com.mycompany
2.2、输入artifactId, 如myproject
2.3、输入version, 如1.0.0-SNAPSHOT
2.4、输入package，如com.mycompany.myproject， 注意此处最好是groupId+artifactId的组合，而不是默认的groupId.

新项目就会生成在./artifactId里

3、测试
 cd ./artifactId
 mvn jetty:run
