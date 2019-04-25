# eureka es例子

- 按照属性and查询
- ES字段与实体属性的映射关系@JsonProperty
如果查询对象中使用了@JsonProperty做映射，所以如果直接返回，前端接收到的将不会是属性值，而是JsonProperty映射的值，这时建议jsonArr中转一遍

参考blog： 
https://blog.csdn.net/Topdandan/article/details/81436141  
https://cloud.tencent.com/developer/article/1343800