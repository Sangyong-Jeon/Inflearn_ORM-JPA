# JPQL - Named 쿼리

## Named 쿼리 - 정적 쿼리

- 미리 정의해서 이름을 부여하여 사용하는 JPQL
- 정적 쿼리
- 어노테이션, XML에 정의
- 애플리케이션 로딩 시점에 초기화 후 재사용
- **애플리케이션 로딩 시점에 쿼리를 검증**
  <br><br>

## Named 쿼리 - 어노테이션

```
@Entity
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.username = :username"
)
public class Member {
    ...
}
```

`@NamedQuery`로 정적 쿼리를 생성했다.<br>
`name`은 쿼리 이름, `query`는 이 쿼리 이름을 실행했을 때 작동하는 JPQL이다.<br>
쿼리 이름은 관례로 `엔티티.메서드명`으로 사용하는데 이는 메서드 명이 중복될 수 있기 때문이다.

```
List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
        .setParameter("username", "회원1")
        .getResultList();
```

사용 방법은 `craeteNamedQuery()`를 사용하여 위에서 만든 `@NamedQuery`의 name을 적으면 된다.

```
@Entity
@NamedQueries({
    @NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.usernae = :username"),
    @NamedQuery(
        name = "Member.count",
        query = "select count(m) from Member m"
})
public class Member {
    ...
}
```

Named 쿼리를 여러개 정의하고 싶으면 `@NamedQueries`를 사용하여 정의한다.
<br><br>

## Named 쿼리 - XML에 정의

짧고 직관적인 것은 어노테이션이지만 엔티티에 Named 쿼리를 많이 작성하는 것보단, 따로 xml파일로 분리하여 관리하는 것이 더 좋을 것 같다.

```
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm" version="2.1">
   <named-query name="Member.findByUsername">
      <query><![CDATA[
            select m
          from Member m
          where m.username = :username
      ]]></query>
    </named-query>
    <named-query name="Member.count">
        <query>select count(m) from Member m</query>
    </named-query>
</entity-mappings>
```

`META-INF/ormMember.xml` 파일을 생성하여 위 코드처럼 Named 쿼리를 설정한다.

```
<persistence-unit name="jpabook" >
 <mapping-file>META-INF/ormMember.xml</mapping-file>
 ...
```

`META-INF/persistence.xml` 파일에서 `<mapping-file>` 태그에 아까 Named 쿼리를 설정했던 `ormMember.xml`을 넣어서 사용하면 된다.
<br><br>

## Named 쿼리 환경에 따른 설정

- 어노테이션과 XML이 같이 설정되어 있으면 XML이 항상 우선권을 가진다.
- 애플리케이션 운영 환경에 따라 다른 XML을 배포할 수 있다.
  <br><br><br>

# 결론

Named쿼리를 Entity에 어노테이션으로 정의하는 것은 별로이다.

Query를 Entity에 넣으면 너무 지저분해지기 때문에 결국 실무에서는 편하고 강력한 Spring Data jpa를 섞어서 쓰기 때문에 이런 방식으로는 잘 쓰지않게 된다.
