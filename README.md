# office-user-auto-create

项目地址：

https://github.com/wuruiwm/msautocreate

为什么要通过 API 自助注册 Microsoft 365 / Office 365 A1 A1P A3 子号？直接登录后台创建子号他不香吗？原因很简单，因为我们手上的 Microsoft 365 / Office 365 A1 A1P A3 全局基本上都是**【非法】**的，说不定哪天全局管理员账号就不能登录后台了，如果你又没有创建 API 权限，那就晚了，要是创建了 API 权限，你还能通过 API 自助创建子号来玩一下

##### **一 创建条件**

- 全局管理员账号
- 域名
- VPS

##### **二 解析域名**

我的域名都是托管在 CloudFlare 的，在 DNS 中添加 A 记录，名称随意，我用的**【ms】**，之后的地址就是**【ms.winvps.eu】**，内容中的 IPv4 填你的**【VPS IP】**，如下图

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-38-1024x294.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-38-1024x294.png)

##### **三 获取 API 权限和相关参数**

进入：

https://portal.azure.com/

登录 Microsoft 365 / Office 365 A1 A1P A3 全局管理员账号

登录后，[点击进入](https://portal.azure.com/#blade/Microsoft_AAD_IAM/ActiveDirectoryMenuBlade/Overview)，**【注册应用程序】**

名称随意，我填的**【API 权限】**，其他的不变，如下图所示

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-39-1024x806.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-39-1024x806.png)

然后点**【注册】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-40-1024x380.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-40-1024x380.png)

获取**【应用程序(客户端) ID / client_id】**和**【目录(租户) ID / tenant_id】**，**【非常重要，注意备份】**

- 应用程序(客户端) ID / client_id：eee14006-540d-42ea-a9bb-vvve85bfacea
- 目录(租户) ID / tenant_id：eeece959-56a2-493f-98da-vvv52ecaea5c

点击左边的**【证书和密码】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-41-1024x486.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-41-1024x486.png)

点击**【新客户端密码】**，说明随意，我用的**【microsoft】**，**【截止期限】**最好选**【从不】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-42.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-42.png)

然后点击**【添加】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-43-1024x195.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-43-1024x195.png)

获取**【客户端密码 / client_secret】**，**【非常重要，注意备份】**

- 客户端密码 / client_secret：PA_rfA69P4aP-j5X20D~7-G1~ozRHzL85j

接下来添加 API 权限

点击左边的**【API 权限】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-44-1024x227.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-44-1024x227.png)

点击**【添加权限】【Microsoft Graph】【应用程序权限】**，查找

- Directory.ReadWrite.All
- User.ReadWrite.All
- RoleManagement.ReadWrite.Directory

添加好后，再点击**【代表 winvps.eu 授予管理员同意】**

[![img](https://www.winvps.eu/wp-content/uploads/2021/02/1-45-1024x270.png)](https://www.winvps.eu/wp-content/uploads/2021/02/1-45-1024x270.png)

授权成功