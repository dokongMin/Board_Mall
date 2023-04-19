## 📌 Summary

<aside>
1️⃣ 개인적인 학습을 위해 게시판 & 쇼핑몰 토이프로젝트를 진행하며 이슈 사항을 정리하고, 화면 구성 대신 API 와 기능 개발을 위주로 진행하고 있는 프로젝트입니다.
<br>
</aside>

<aside>
2️⃣ 현재까지도 개발이 진행중인 프로젝트로, 기능의 고도화를 위해 관련 기술을 학습하고, 이를 적용시켜보는 방식으로 진행중입니다.
<br>
</aside>

<br>
프로젝트 기간 : `2023.03 ~`

### 🛠️ Specification

- Java
- Spring boot
- JPA
- QueryDSL
- MySQL, H2
- Github Action (CI/CD)
- Git
- Redis

## 💡 Tech Issues

1. **트랜잭션과 프록시**

```java
@Transactional
public void createUserListWithoutTrans(){
    for (int i = 0; i < 10; i++) {
// 트랜잭션 AOP 적용 X
        **createUser**(i);
    }
    throw new RuntimeException();
}

@Transactional
public User createUser(int index){
    User user = User.builder()
            .build();
   
    userRepository.save(user);
    return user;
}
```

동일한 클래스에서 호출한 메소드가 트랜잭션이 적용되지 않고 실행되는 문제가 있었습니다. 
이는 **내부 메소드**의 경우, **프록시가 아닌 실제 타겟이 호출**되기 때문에 적용되지 않는 문제였습니다.
트랜잭션 전파방식 설정 또는 메소드를 분리 하는 방법 중, 해당 메소드를 분리하여 해결했습니다.

위 코드에서 외부 메소드의 경우, @Transactional 을 통해 스프링 AOP 의 프록시 객체가 생성돼서 실행되게 됩니다. 반면 `createUser` 는 프록시 객체가 아닌 **타겟**의 메소드가 호출되기 때문에 별도의 트랜잭션을 갖고 있지 않아서 호출한 메소드에서 예외가 발생하는 경우, 저장이 되지 않습니다.

2. **동시성 이슈**

<img width="40%" src="https://user-images.githubusercontent.com/77479127/232936216-c0737c63-80f4-430e-9ee7-e70491e5660e.png"/>

동시에 여러명이 주문하는 경우에 대해 어떻게 처리할까 고민했었고, 처음에는 synchronized ****키워드를 사용하려 했습니다.  
하지만 서버가 1대 이상인 경우 그림과 같은 동시성 문제가 발생한다는 것을 확인했습니다.

다른 해결 방법으로는 크게 데이터베이스 **Lock** 을 사용하는 방법과, **분산 락**을 이용해서 해결하는 방법이 있었습니다.
Optimistic Lock 은 재시도 로직에 대한 복잡성 등 구현이 힘들 것 같아 **Pessimistic Lock** 방법을 택했고, 성공적으로 **동시성 이슈를 제어**했습니다.

3. **Redis 를 이용한 조회수 증가**

기존의 게시글 조회수 증가 로직은 게시글을 조회했던 회원이 다시 조회하는 경우, 중복체크를 하지 않고 조회수 카운트가 올라가도록 구현이 되어 있었습니다.

Redis 의 Key 를 이용해서, 만약 “userA” 라는 회원이 1 번 게시글을 조회한 경우, 이를 
“username” + “boardId” 의 조합으로 키를 생성해서 이미 해당 키가 존재하는 경우 조회수를 증가시키지 않는 로직을 구현했습니다.

TimeOut 은 24 시간으로 설정하여 24시간이 지난 후에는 Key 가 삭제되어 다시 조회 카운트를 올릴 수 있도록 하였습니다.

4. **선착순 쿠폰 발급 시스템**

선착순 쿠폰 발급 시스템을 구현하기 위해서는 고려할 사항이 2가지가 있었습니다.

1. **동시성 문제**
2. **순간적으로 발생하는 과도한 트래픽 문제**

Redisson 의 분산 락을 이용해서 동시성 문제를 제어하였고, Redis 를 이용해서 순간적으로 몰리는 트래픽에 대응하였습니다.

동시성을 제어하는 방법에는 Redisson 과 Lettuce 2개의 선택지가 존재했습니다.
Spring 의 경우 기본적으로 지원해주는 것은 Lettuce 이지만 Lettuce 의 경우, 반드시 **스핀 락**의 형태로 구현해야 한다는 단점이 있었습니다.

반면 Redisson 을 이용하는 방식은 **메시지 브로커**를 이용하는 방식으로, **pub** 와 **sub** 두 가지 명령을 통해 락을 획득함으로써 동시성을 제어하고, DB 의 Pessimistic Lock 과는 달리 **타임아웃을 자동으로 처리**해주어 데드락 발생 가능성이 줄어든다는 이점을 가지고 있기 때문에 Redisson 을 선택했습니다.

---

과도한 트래픽 문제는 Redis 를 이용해서 해결하려 했습니다.

Redis 는 in-memory 구조이기 때문에 속도가 빠르다는 장점이 있는 대신, 서버에 문제가 생기면 데이터의 유실이 생길 수 있기 때문에 주의해야 한다는 단점이 있습니다.

→ 데이터의 유실이 생기는 단점은 추후 Slave 를 두어 **복제본을** **만들어** 두어 **해결**하면 된다고 생각했기 때문에 Redis 를 사용하자고 생각했습니다 & Log.

전체적인 흐름은 다음과 같이 생각했습니다.

기존의 흐름이 다음과 같이 대기열과 쿠폰 발급이 한번에 이루어진다면,

<img width="40%" src="https://user-images.githubusercontent.com/77479127/232936670-e16fdde8-7642-43f7-bb71-aef01520375e.png"/>

Redis 를 이용해서 다음과 같이 분리를 하면 순간적으로 몰리는 트래픽을 해결할 수 있을 것 같다고 생각했습니다.

<img width="40%" src="https://user-images.githubusercontent.com/77479127/232936707-3ff15274-1e29-4525-bb74-3300bef1abe2.png"/>

**선착순 대기열**에 들어가는 서버를 별도로 두어 Redis 를 이용해서 구현하면 **in-memory 구조 이기 때문에 빠르게 처리**가 될 것이라고 생각했고

대기열에서 정보를 꺼내서 쿠폰을 발급하는 경우는 쿠폰 발급 서버를 따로 두면 급격하게 몰리는 **트래픽으로부터 분리**되어서 방어가 될 것이라고 생각했습니다.

---

하나의 서버로 구현하였고, 
`[key : ”couponName” , value : “username” , score : currentTimeMills]`로 키를 설정하여 score 순서대로 값을 정렬한 뒤, 10명씩 끊어서 현재 큐에 들어가 있는 유저를 찾아내고 쿠폰을 발급하도록 하였습니다. 

5. **write-back 방식으로 조회수 구현**

게시글에 조회할 때마다 조회수를 증가하는 쿼리를 즉시 보내주게 되면 **불필요하게 많은 트래픽**을 발생시킬 수 있다고 생각했습니다.
이를 해결하기 위해 조회수를 count 해놓았다가, 일정 주기마다 쿼리를 보내주는 **write-back** 방식으로 구현해보았습니다.

조회수 증가 조건은 다음과 같이 설정했습니다.

1. 유저는 게시글 조회 후, 24 시간이 지난 후에 다시 해당 게시글의 조회수를 증가시킬 수 있습니다.
2. 10분 간격으로 조회수를 업데이트 해줍니다.

조회수를 관리하기 위해 Redis 의 여러 DataType 중 **Hash** 자료구조를 선택했습니다. Hash 에는 **hincrby** 라는 명령어를 통해 입력한 숫자만큼 더해주고, 만약 키가 존재하지 않는다면 키를 생성해서 값을 더해주는 기능이 존재하는데 이는 조회수를 증가하는 기능과 잘 어울린다고 생각해 Hash 로 선택했습니다.

---

`[ key : “boardId + boardTitle” , field : “boardTitle”, increase : 1 ]` 형태로 키를 설정했으며, @Scheduled 를 통해 일정 주기마다 시스템에서 쿼리문을 보내도록 구현했습니다.
