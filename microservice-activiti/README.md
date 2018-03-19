# acitiviti工作流服务

## 组件概述
activiti组件是工作流制定、发布、执行、查询等功能的核心组件。  
在封装的过程中，希望尽量减少[流程设计界面](#工作流模型维护)中设置的复杂度，更多的功能通过系统封装实现。
  
此组件大致包含以下功能模块：  
- [工作流模型维护](#工作流模型维护) 
- [工作流执行](#工作流执行) 
- [自定义工作流节点受理人](#自定义工作流节点受理人) 
- [工作流查询](#工作流查询) 
- [运行中流程管理](#运行中流程管理)   

项目采用swagger提供在线接口信息，发布后可以查看接口说明：  
http://localhost:8484/microservice-activiti/swagger-ui.html  
## 工作流模型维护
- 模块说明  
工作流模型（Activiti-Modeler），是activiti发布的线流程设计器，可以让业务人员用画图的方式自己设计定义工作流，并部署到工作流引擎中。  
modeler接口全部在：com.java.activiti.modeler包下，静态文件存放在resource/activiti、resource/public下。  
通过swagger界面，可以看到此模块的接口列表。  
- 注意事项  
1.系统采用微服务架构，网关使用zuul进行API GATEWAY。  
由于zuul不支持静态资源的转发，所以工作流在线编辑界面不能通过zuul访问，只能访问组件地址，这点可以在打开工作流编辑的界面中通过URL查看。  
2.在modeler中画图的时候，例如拖拽了一个userTask，不要缩小图片，如果缩小会导致流程在运行的过程中查看流程执行位置的时候，无法显示节点描述信息里面的文字。    
- 发布流程  
在modeler界面编辑好流程并保存之后，将流程下载到本地，添加到resource/processes路径下，运行activiti组件即可。

## 工作流执行
- 模块说明  
此模块主要提供两个接口：  
1.开始一个工作流（startWorkflow）  
2.执行工作流（completeTask）  
- 使用方法  
工作流执行接口：com.java.activiti.business.ctrl.WorkflowExecutController  
1.先调用“开始工作流”接口，此时系统会执行一个工作流实例。  
2.然后调用“执行工作流”接口，即可执行工作流至下一个节点。  
- 场景实现    
1.分支流转，即排他网关（exclusive gateway）  
当工作流执行下一步的时候，希望能根据相关参数等执行不同的流程分支，此时需要在当前节点下面添加排他网关。  
操作：在[工作流模型维护](#工作流模型维护)界面中，给userTask下一节点添加exclusive gateway，然后在不同的分支上设置“flow-condition”属性为:${OPINION=='判断参数'}，当调用工作流执行接口时，将判断参数OPINION传入工作流执行接口（completeTask）即可。  
2.会签实现  
工作流执行的过程中，常规的userTask向下执行成功一次后，工作流就会进入下一节点；如果需要某个节点被多次执行，即可理解为会签。  
实际场景：版本发布中某一节点需要被DBA、安全审核两个角色同时审核，当都同意后，执行至下一节点；当有任何不同意，则流程结束。  
操作：在modeler界面中，设置userTask的以下属性：  
&nbsp;&nbsp;&nbsp;“Multi-instance type”设置为“Parallel”，表示并行会签  
&nbsp;&nbsp;&nbsp;“Collection(Multi-instance)”设置为“${ASSIGNEE}”，此处声明会签人员集合变量名称  
&nbsp;&nbsp;&nbsp;“Element-variable(Multi-instance)”设置为“assignee”，表示会签人员集合中每个元素的变量   
&nbsp;&nbsp;&nbsp;“Assignments”设置为“${assignee}”，表示会签受理对象引用会签人员元素变量  
参考代码：查看工作流执行接口（completeTask）中会签分支的实现。  

## 自定义工作流节点受理人
- 模块说明  
工作流的执行，实质上是生成一系列任务记录的过程（可观察activiti自带表act_ru_task）；  
在执行的过程中，我们需要将某个任务指定给某些人（即指定受理人）,此时我们需要有这些事情需要考虑：  
1.每个节点的受理人如何能做到可配置化，且能做到足够灵活？   
2.程序如何实现，会签节点和普通userTask是否能以相同的方式实现？    
3.用户查询自己相关的任务如何实现？ 
- 任务受理对象设计  
在工作流执行的过程中，某一节点的受理人可能有以下三种情况：  
1.是固定的（例如：流程中某一节点不管什么情况下永远都被某些人审批）  
2.半固定固定的（例如：同样都是版本发布申请流程，紧急版本申请的某一节点被固定的一批人审批，非紧急的此节点被另外一批人审批）  
3.是动态的（例如：流程中某一节点是申请人所在的项目的项目经理审批）  
针对前两种情况，我们通过封装实现可配置化，针对第三种情况，我们在编程的时候动态指定。  
可配置化实现过程：  
在实现可配置化之前，我们考虑到某一任务在指定受理人的同时，我们希望不单单指定给用户，也能指定给角色，或者混合指定，所以我们加入了“受理对象”的概念。  
受理对象，是指某一任务的受理者的抽象；任务指定给了受理对象，然后受理对象中分配了具体的角色、或用户后，最终就等同于受理对象中包含的用户收到了任务。  
具体实现：我们通过读取系统中已经发布的工作流信息，然后解析各个节点信息；让管理员可在界面配置某个工作流的某一节点受理对象，并且可以配置受理对象下的具体受理人、角色。  
此处涉及到两个自定义表：csm_act_assignee、csm_act_assignee_object。  
csm_act_assignee存储的是流程节点的信息。  
csm_act_assignee_object存储的是csm_act_assignee中节点的具体受理对象信息。  
这两个表属于主子表关系，一对多。  
当某一任务需要动态匹配受理对象时，我们将任务的受理者指定为csm_act_assignee表的主键，这样便通过csm_act_assignee表实现了受理人的灵活配置和进一步扩展。  
至此，已经满足第一种情况下自定义流程节点受理者实现，但是如何实现第二种半固定情况呢？  
我们在csm_act_assignee表中扩展了一个字段assignee_code，我们可以给不同的情况分配不同的code，当业务组件在执行工作流的时候
根据不同的情况传入不同的code即可。  
- 任务受理对象实现  
接口信息：com.java.activiti.business.ctrl.ProcessAssigneeController    
上面讲述了如何通过自定义表实现受理人的扩展，在将受理人分配给用户任务的时候，有两种情况是需要不同的实现：  
1.常规userTask任务；我们是通过执行完任务后，根据新生成的任务，去匹配csm_act_assignee表中的节点信息，将受理人字段指定为csm_act_assignee表主键。  
2.会签任务（并行会签，目前暂时未设计串行会签）；由于会签任务会给一个节点生成N个并行任务，N是动态的，且是在执行上一个任务后，根据受理对象的集合大小动态分配的，所以我们不能按照1中先执行再分配的方式，
我们需要在任务执行前就指定会签任务的受理者，当任务被执行后，会生成N条分配好受理人的任务。    
实现代码：针对这两种不同的情况，我们封装了一个流程受理对象类（WorkFlowAssignee.java）,无论执行以上哪种情况的任务，都要传递此参数。
  
WorkFlowAssignee.java属性解析：   
``` java
private Boolean multiSign = false;   
```
当前任务执行后，下一个节点是否是多人会签节点 
``` java
private ArrayList<String> assigneeCode;    
```
匹配流程节点受理对象的标识，会匹配csm_act_assignee表的assignee_code字段，当不是多人会签的时候，只会按照第一个下标进行匹配  
``` java
private String assignee;     
```
受理人，单个用户ID；当isMultiSign=false时，让程序自定义受理人  
需要注意的是，当系统中自定义流程节点受理对象不为空时，此属性会被覆盖  
``` java
private ArrayList<String> multiAssignees;
``` 
会签受理人集合；当isMultiSign=true时，程序才会解析此属性
需要注意的是，当系统中自定义流程节点受理对象不为空时，此属性会被覆盖    
``` java
private String multiActivitiCode;    
``` 
会签流程节点CODE（当isMultiSign=false时必须传递）  
在程序执行时，当下一节点是会签任务时，当前任务并不知道下一节点的code，但是自定义流程受理对象必须要根据流程code和节点code才能匹配出受理对象，所以要传递此参数，会签节点要麻烦一些。  

## 工作流查询
- 模块说明  
工作流查询模块分为以下功能：  
1.我的待办任务、我发起的任务、我参与的任务查询（界面上“我的空间”模块）  
2.任务当前审批人、审批记录查询  
3.查询工作流执行到哪一步，并高亮显示  
- 实现过程  
接口信息：com.java.activiti.business.ctrl.TaskQueryController    
由于工作流执行后，所以的信息都会当做存量数据放在activiti历史表中，分析这些表的关系以及查询出来比较复杂，所以系统加入了自定义表csm_flow_task、csm_flow_approve_records。  
csm_flow_task表记录的是某一个申请记录的大体信息，包含流程名称、发起人等  
csm_flow_approve_records表记录的是每次审批的记录  
在工作流执行接口中，开始一个工作流时会对表csm_flow_task进行数据的新增；  
然后在每次执行的过程中，更改csm_flow_task表对应的状态信息；同时也会对审批记录表csm_flow_approve_records信息进行新增。  
在系统中“我的空间”模块，对工作流数据进行查询时，实际上是通过csm_flow_task表关联[自定义工作流节点受理人](#自定义工作流节点受理人)中的两个自定义表，查询出当前用户符合条件的数据。  

## 运行中流程管理
- 模块说明  
1.查询运行中的任务列表  
2.修改运行中任务列表的受理人  
- 实现过程  
接口信息：com.java.activiti.business.ctrl.RunTaskManagementController  
查询出来任务后，更改受理者（目前仅支持更改为用户ID），通过更改activiti表act_ru_task的ASSIGNEE_字段即可实现。      

