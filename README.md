# Name-Of

---

## 使用场景

---

如写Mongo语句，要查询名字时: `collection.find(eq("name", "songjhh"))`

名字（name） 是通过字符串手写进来的，这会造成一个问题：我需要修改字段将 `name` 改为 `nickname` 时，改查询语句也需要同步改，否则不生效。

当修改的地方很多时，难免会有遗漏，造成一些不好排查的 Bug 出现。

所以希望能够有办法获取类的字段拼写，而不需要自己手写这个字符串，这样在修改字段时通过 IDE 的重构功能修改，就能自动将所有引用同时修改，减小了排查字段的工作量和降低了产生 Bug 的可能性。

## 用法

---

### 1. 初始化

```java
// 初始化，会将 id 转化为 _id，专为 MongoDB 准备
NameOf.mInit(User.class);
// 初始化
NameOf.init(User.class);
```

### 2. 解析字段

```java
// 将会解析 name 这个字段
NameOf.mInit(User.class).field(User::getName);
// 也可解析多个字段，如 name 和 age
NameOf.mInit(User.class).field(User::getName).field(User::getAge);
```

### 3. 输出

```java
// str() 方法将会输出字符串，如: "name"
NameOf.mInit(User.class).field(User::getName).str();
// array() 方法将会输出字符串数组，如: {"name", "age"}
NameOf.mInit(User.class).field(User::getName).field(User::getAge).array();
```

### 4. 特别用法 - any()

```java
// any() 方法将会查询自定义字符串，如: "name.post"
NameOf.mInit(User.class).field(User::getName).any(".").field(User::getPost).str();
```

## 局限和未来发展

---

- 不支持跨类操作

比如：user.address.name (用户的地址名字)

```java
// 输出 address.name，需要用到字符串拼接
NameOf.mInit(User.class).field(User::getAddress).any(".").str()
        +NameOf.mInit(Address.class).field(Address::getName).str()
```

希望以后可以达到跨类无需字符串拼接的效果。


## SonarQube

---

name-of: 7b83e433b53953893bc0f2d289ec4946fab08c63

```java
mvn sonar:sonar \
  -Dsonar.projectKey=common_name-of_AXmn-quqEKhU3bS9eNIG \
  -Dsonar.host.url=http://139.159.219.92:10088 \
  -Dsonar.login=7b83e433b53953893bc0f2d289ec4946fab08c63
```