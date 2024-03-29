---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by zhenwu.
---

-- 1.参数列表
-- 1.1 用户token
local token = ARGV[1]
-- 1.2 用户token有效期
local ttl = ARGV[2]
-- 1.3 用户账号
local userAccount = ARGV[3]
-- 1.4 用户信息
local userInfo = ARGV[4]

-- 2.数据key
-- 2.1 用户 token key
local tokenKey = 'login:token:' .. token
-- 2.1 用户 account key
local accountKey = 'login:online:' .. userAccount

-- 3.存储数据
redis.call('SET', tokenKey, userInfo)
redis.call('EXPIRE', tokenKey, ttl)
redis.call('SET', accountKey, token);
redis.call('EXPIRE', accountKey, ttl)

return 0