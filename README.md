# 项目介绍

API开放平台是一个接口管理平台，管理员可以在后台维护接口信息，用户可以在API开放平台注册后，获取平台提供的SDK来调用平台提供的接口。

# 项目演示

前端项目`api-frontend`地址：https://github.com/POILKJHMNBV/api-frontend

## 管理员界面
![管理员界面](https://github.com/POILKJHMNBV/api-backend/assets/90057507/14d80e15-14da-414f-b868-ec66f5593637)
接口管理：维护API开放平台的接口信息
用户管理：管理API开放平台注册的用户信息
接口分析：用户调用接口的统计信息

## 普通用户界面
![普通用户界面](https://github.com/POILKJHMNBV/api-backend/assets/90057507/b95cac36-6cba-4f3d-819f-b6896e0e0d80)
用户可以浏览API开放平台提供的接口信息，查看调用每个接口的详情信息。

## 个人中心界面
![个人中心界面](https://github.com/POILKJHMNBV/api-backend/assets/90057507/dfa237ea-5235-4914-a835-dedcef127104)
用户可以在个人中心界面查看自己的公钥，accessKey，secertKey信息。用户只需通过使用API开放平台提供的SDK中配置这些信息以及接口的token信息便可以轻松调用API开放平台提供的接口。

# 组织结构

```lua
openapi
├── api-common -- 工具类及通用代码
├── api-sdk -- API开放平台提供的SDK
├── api-gateway -- API网关
├── api-interface -- API开放平台提供的接口
└── api-backend -- API开放平台后台管理系统接口
```

更多内容，敬请期待……
