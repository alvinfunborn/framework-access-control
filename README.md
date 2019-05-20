#### abac,rbac权限控制框架
1. 支持角色继承,数据组,行为组

##### usage
1. 用户与角色需要额外维护
2. 权限控制规则默认驻留内存, 可自定义RuleRepository实现
3. 角色继承关系默认驻留内存, 可自定义RoleHierarchyRepository实现
4. 数据组关系默认驻留内存, 可自定义DataGroupRepository实现
5. 行为组关系默认驻留内存, 可自定义ActionGroupRepository实现

###### config
```$xslt
@Configuration
public class AccessControlConfig {

    @Bean
    public Controller controller() {
        return new StandardControllerBuilder()
                .withRuleRepository(new DefaultRuleRepository())
                .withActionHierarchyRepository(null)
                .build();
    }
}
```

###### service
```$xslt
@Service
public class TestService {

    @Autowired
    private Controller controller;

    public void test() {
        controller.addRoleHierarchy(new Hierarchy("admin", "default"));
        controller.addActionGroup(new Group("read", "readTitle"));
        controller.addActionGroup(new Group("read", "readContent"));
        controller.addDataGroup(new Group("text", "text1"));
        controller.addDataGroup(new Group("text", "text2"));
        Condition ageLte18Condition = new Condition("ageLte18") {
            @Override
            public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
                MySubject mySubject = (MySubject) subject;
                int age = mySubject.getAge();
                if (age >= 18) {
                    return Result.ofPermit();
                }
                return Result.ofDeny("too young.");
            }
        };
        Condition textPrimeCondition = new Condition("textPrime") {
            @Override
            public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
                MySubject mySubject = (MySubject) subject;
                MyResource myResource = (MyResource) resource;
                if (myResource.isNeedPrime()) {
                    if (mySubject.isPrime()) {
                        return ageLte18Condition.onCondition(subject, resource).and(Result.ofPermit());
                    } else {
                        return ageLte18Condition.onCondition(subject, resource).and(Result.ofDeny("prime needed."));
                    }
                }
                return ageLte18Condition.onCondition(subject, resource).and(Result.ofPermit());
            }
        };
        controller.addRule(Rule.ofPermit("name", "default", "text1", "read", ageLte18Condition));
        controller.addRule(Rule.ofPermit("name", "default", "text2", "readTitle", ageLte18Condition));
        controller.addRule(Rule.ofPermit("name", "default", "text2", "readContent", textPrimeCondition));
        controller.addRule(Rule.ofPermit("name", "admin", "text2", "read", Condition.alwaysOnCondition()));

        System.out.println(controller.control(new MySubject("default", 15, false), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 15, true), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 15, false), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("default", 15, true), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("default", 15, false), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("default", 15, true), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("default", 15, false), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 15, true), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 15, false), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("default", 15, true), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("default", 15, false), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("default", 15, true), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("default", 20, false), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 20, true), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 20, false), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("default", 20, true), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("default", 20, false), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("default", 20, true), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("default", 20, false), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 20, true), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("default", 20, false), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("default", 20, true), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("default", 20, false), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("default", 20, true), new MyResource("text2", true), "read"));

        System.out.println(controller.control(new MySubject("admin", 15, false), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 15, false), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 15, false), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("admin", 15, false), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 15, false), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 15, false), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("admin", 15, true), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new Resource("text1"), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new Resource("text1"), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new Resource("text1"), "read"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new MyResource("text2", true), "readTitle"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new MyResource("text2", true), "readContent"));
        System.out.println(controller.control(new MySubject("admin", 20, false), new MyResource("text2", true), "read"));
        System.out.println(controller.control(new MySubject("admin", 20, true), new MyResource("text2", true), "read"));
    }

    class MySubject extends Subject {
        private int age;
        private boolean prime;

        public MySubject(String role, int age, boolean prime) {
            super(role);
            this.age = age;
            this.prime = prime;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isPrime() {
            return prime;
        }

        public void setPrime(boolean prime) {
            this.prime = prime;
        }
    }

    class MyResource extends Resource {
        private boolean needPrime;

        public MyResource(String data, boolean needPrime) {
            super(data);
            this.needPrime = needPrime;
        }

        public boolean isNeedPrime() {
            return needPrime;
        }

        public void setNeedPrime(boolean needPrime) {
            this.needPrime = needPrime;
        }
    }
}
```
