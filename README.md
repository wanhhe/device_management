# sicau_device_management

四川农业大学设备管理项目后端代码仓库



### 接口

> 不想写了

##### DeviceController

| 功能                               | 返回值                                                       |      |
| ---------------------------------- | ------------------------------------------------------------ | ---- |
| 导出设备基本信息                   | excel表格                                                    |      |
| 选择时间段，导出设备的借用历史数据 | excel表格                                                    |      |
| 设备信息一览                       | 返回总设备数量、正在使用的中的设备数量、超时未归还的设备数量 |      |
| 每台设备现在的状态信息             | void                                                         |      |
| 某台设备的租借数据                 | 租借数据                                                     |      |
| 修改某台设备使用状态               | void                                                         |      |



##### DeviceTypeController

| 功能                   | 返回值   |      |
| ---------------------- | -------- | ---- |
| 按金额查询             | 设备列表 |      |
| 按所属老师查询         | 设备列表 |      |
| 按系室查询             | 设备列表 |      |
| 按设备名查询           | 设备列表 |      |
| 该类设备某周的使用情况 |          |      |



##### DeviceImgController



##### LabController

| 功能           | 返回值                                     |      |
| -------------- | ------------------------------------------ | ---- |
| 可选择系室列表 | 按教学楼、楼层分类后返回所有拥有设备的系室 |      |
|                |                                            |      |
|                |                                            |      |
|                |                                            |      |



##### TeacherController

| 功能               | 返回值                 |      |
| ------------------ | ---------------------- | ---- |
| 可选择老师列表     | 返回所有拥有设备的老师 |      |
| 指导老师审核申请   | 是否通过，未通过原因   |      |
| 管理员审核申请     | 同上                   |      |
| 设备管理人审核申请 | 同上                   |      |



##### StudentController

| 功能                       | 返回值 |      |
| -------------------------- | ------ | ---- |
| 修改账号密码               | void   |      |
| 封禁下属研究生账号         | void   |      |
| 删除下属研究生账号         | void   |      |
| 延长下属研究生账号使用时间 | void   |      |



##### RentApplyController

| 功能 | 返回值 |      |
| ---- | ------ | ---- |
|      |        |      |
|      |        |      |
|      |        |      |
|      |        |      |



##### UserController

| 功能           | 返回值 |      |
| -------------- | ------ | ---- |
| 用户修改密码   | void   |      |
| 用户修改手机号 | void   |      |
|                |        |      |
|                |        |      |

