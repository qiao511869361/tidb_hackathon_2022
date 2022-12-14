# tidb_hackathon_2022_rfc

# 五湖四海团队

队长：张桥 

队员：書源，张泽龙，庾子浩

## 仓库
https://github.com/qiao511869361/tidb_hackathon_2022.git

# SSO 实现 TiDB 登录权限校验

## 背景和动机

在实践中发现，互联网的时代，分散化的各种各样的系统越来越多，已经让人目不暇接，这时候我们总是一个个登录不一样的系统，由此产生的账号记录和消耗，已经成为一个重点的麻烦点。这时候我们需要一个统一的单点登录系统，这是由真实的需求驱动，因为通过单点系统我们可以将散落在不同地方的系统，整个统一管理起来，真正达到一次登录到处浏览，而不用登录一个就需要再次登录的尴尬处境。


## 项目介绍

### 单点登录

  单点登录的英文名叫做：Single Sign On（简称SSO），指在同一帐号平台下的多个应用系统中，用户只需登录一次，即可访问所有相互信任的系统。简而言之，多个系统，统一登陆。
  ![图片](https://img-blog.csdnimg.cn/f3f815973ee34efcaf3b99a70ebca2a6.png)

### 登录原理

sso作为一个独立的认证中心，所有子系统都通过认证中心的登录入口进行登录，登录时带上自己的地址，子系统只接受认证中心的授权，授权通过令牌（token）实现，sso认证中心验证用户的用户名密码正确，创建全局会话和token，token作为参数发送给各个子系统，子系统拿到token，即得到了授权，可以借此创建局部会话，局部会话登录方式与单系统的登录方式相同。

### proxySQL

![图片](https://download.pingcap.com/images/docs-cn/develop/proxysql_config_flow.png)

### 与TiDB的融合

sso系统会作为一个单独的认证系统进行运行，有sso系统进行系统的账号的权限的验证，如下：
![image](https://user-images.githubusercontent.com/40740009/195037555-67f32e85-f3f4-4a03-936e-6df1746d2c37.png)


## 项目设计

### 思路（来自TiDB想法库）

编写 OAuth（或JWT） 服务， 构建 Token <-> 临时用户名/密码 映射，仅此服务知晓 Root 账户，进行临时用户的管理，如自动续期，提醒改密，动态密码等功能 与常见 ORM/Driver 进行组合，如直接使用 JDBC 配置 OAuth 登录，自动完成用户名/密码获取、登录等操作 与 使用 ProxySQL 与 TiDB 完成多租户 3 组合，完成 SSO + 多租户

### 详细单点登录原理流程

![图片](https://img-blog.csdnimg.cn/0cd6407fe18740d7818abc576b36d684.png)

---

1. 用户访问系统1的受保护资源，系统1发现用户未登录，跳转至sso认证中心，并将自己的地址作为参数
2. sso认证中心发现用户未登录，将用户引导至登录页面
3. 用户输入用户名密码提交登录申请
4. sso认证中心校验用户信息，创建用户与sso认证中心之间的会话，称为全局会话，同时创建授权令牌
5. sso认证中心带着令牌跳转会最初的请求地址（系统1)
6. 系统1拿到令牌，去sso认证中心校验令牌是否有效
7. sso认证中心校验令牌，返回有效，注册系统1
8. 系统1使用该令牌创建与用户的会话，称为局部会话，返回受保护资源
9. 用户访问系统2的受保护资源
10. 系统2发现用户未登录，跳转至sso认证中心，并将自己的地址作为参数
11. sso认证中心发现用户已登录，跳转回系统2的地址，并附上令牌
12. 系统2拿到令牌，去sso认证中心校验令牌是否有效
13. sso认证中心校验令牌，返回有效，注册系统2
14. 系统2使用该令牌创建与用户的局部会话，返回受保护资源 

---

### 融合方案

在sso系统运行中，与proxySQL进行融合使用：
![image](https://user-images.githubusercontent.com/40740009/195037555-67f32e85-f3f4-4a03-936e-6df1746d2c37.png)

## 项目分析

### 在实践单点登录中的问题

1. 在现场执行中存在需求的理解不一致，以及mvp版本的简陋性，需要仅仅完成单点登录功能，可能存在硬编码
2. 在Hackathon 方式执行，时间紧任务重的情况下，会有优先级，例如：与proxySQL的对接属于重要项，单点功能的实现属于重要项
3. 团队之间的磨合可能存在问题，导致任务的失败的情况



### 现场实现TiDB单点登录代理方案执行

#### 人员分配

一号战斗小组：张桥，書源

二号战斗小组：张泽龙，庾子浩

#### 方案执行

1. 一号战斗小组进行单点登录对接ProxySQL对接，对接倾向于最简单方案
2.  二号战斗小组进行单点登录的实现，倾向于最简单方案
3.  单点登录实现中用最简单登录流程，实现登录和推出
4. 对接Proxy中，以最简单方案实现
5. 因为mvp内裤版本，目标只为最初始版本的功能实现，不为花里胡哨的功能，我们的最终目标就是以最简单的方式实现功能





